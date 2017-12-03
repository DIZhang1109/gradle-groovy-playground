package utility

import org.w3c.dom.Attr
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.diff.Comparison
import org.xmlunit.diff.ComparisonResult
import org.xmlunit.diff.Diff
import org.xmlunit.diff.DifferenceEvaluator
import org.xmlunit.diff.DifferenceEvaluators

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

/**
 * Created by Di on 1/12/17.
 * XmlComparisonService
 */
class XmlComparisonService {

    static void compareXml(String type, String actualXml, String expectedXml) {
        Diff diff = null

        if (type == 'Encryption') {
            diff = DiffBuilder.compare(expectedXml)
                    .withTest(actualXml)
                    .checkForSimilar()
                    .ignoreComments()
                    .ignoreWhitespace()
                    .normalizeWhitespace()
                    .withDifferenceEvaluator(DifferenceEvaluators.chain(new IgnoreAttributeDifferenceEvaluator('wsu:Id'),
                    new IgnoreAttributeDifferenceEvaluator('Id'), new IgnoreAttributeDifferenceEvaluator('URI'),
                    new IgnoreElementDifferenceEvaluator('xenc:CipherValue')))
                    .build()
        } else if (type == 'Signature') {
            diff = DiffBuilder.compare(expectedXml)
                    .withTest(actualXml)
                    .checkForSimilar()
                    .ignoreComments()
                    .ignoreWhitespace()
                    .normalizeWhitespace()
                    .withDifferenceEvaluator(DifferenceEvaluators.chain(new IgnoreAttributeDifferenceEvaluator('Id'),
                    new IgnoreAttributeDifferenceEvaluator('wsu:Id'), new IgnoreAttributeDifferenceEvaluator('URI'),
                    new IgnoreElementDifferenceEvaluator('ds:DigestValue'), new IgnoreElementDifferenceEvaluator('ds:SignatureValue')))
                    .build()
        } else if (type == 'Timestamp') {
            diff = DiffBuilder.compare(expectedXml)
                    .withTest(actualXml)
                    .checkForSimilar()
                    .ignoreComments()
                    .ignoreWhitespace()
                    .normalizeWhitespace()
                    .withDifferenceEvaluator(DifferenceEvaluators.chain(new IgnoreAttributeDifferenceEvaluator('wsu:Id'),
                    new IgnoreElementDifferenceEvaluator('wsu:Created'), new IgnoreElementDifferenceEvaluator('wsu:Expires')))
                    .build()
        } else if (type == 'Username') {
            diff = DiffBuilder.compare(expectedXml)
                    .withTest(actualXml)
                    .checkForSimilar()
                    .ignoreComments()
                    .ignoreWhitespace()
                    .normalizeWhitespace()
                    .withDifferenceEvaluator(DifferenceEvaluators.chain(new IgnoreAttributeDifferenceEvaluator('wsu:Id'),
                    new IgnoreElementDifferenceEvaluator('wsse:Nonce'), new IgnoreElementDifferenceEvaluator('wsu:Created')))
                    .build()
        } else if (type == 'All') {
            diff = DiffBuilder.compare(expectedXml)
                    .withTest(actualXml)
                    .checkForSimilar()
                    .ignoreComments()
                    .ignoreWhitespace()
                    .normalizeWhitespace()
                    .withDifferenceEvaluator(DifferenceEvaluators.chain(new IgnoreAttributeDifferenceEvaluator('wsu:Id'),
                    new IgnoreElementDifferenceEvaluator('wsse:Nonce'), new IgnoreElementDifferenceEvaluator('wsu:Created'),
                    new IgnoreElementDifferenceEvaluator('wsu:Expires'), new IgnoreAttributeDifferenceEvaluator('Id'),
                    new IgnoreAttributeDifferenceEvaluator('URI'), new IgnoreElementDifferenceEvaluator('ds:DigestValue'),
                    new IgnoreElementDifferenceEvaluator('ds:SignatureValue'), new IgnoreElementDifferenceEvaluator('xenc:CipherValue')))
                    .build()
        }

        assertThat 'Difference: ' + diff.toString(), diff.hasDifferences(), is(false)
    }
}

class IgnoreAttributeDifferenceEvaluator implements DifferenceEvaluator {
    private String attributeName

    IgnoreAttributeDifferenceEvaluator(String attributeName) {
        this.attributeName = attributeName
    }

    @Override
    ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) {
            return outcome
        }
        final Node controlNode = comparison.getControlDetails().getTarget()
        if (controlNode instanceof Attr) {
            Attr attr = controlNode as Attr
            if (attr.getName() == attributeName) {
                return ComparisonResult.SIMILAR
            }
        }
        outcome
    }
}

class IgnoreElementDifferenceEvaluator implements DifferenceEvaluator {
    private String elementName

    IgnoreElementDifferenceEvaluator(String elementName) {
        this.elementName = elementName
    }

    @Override
    ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) {
            return outcome
        }
        final Node controlNode = comparison.getControlDetails().getTarget()
        final Node testNode = comparison.getTestDetails().getTarget()
        if (controlNode.getParentNode() instanceof Element && testNode.getParentNode() instanceof Element) {
            Element controlElement = (Element) controlNode.getParentNode()
            Element testElement = (Element) testNode.getParentNode()
            if (controlElement.getNodeName() == elementName) {
                if (controlElement.getTextContent() != testElement.getTextContent()) {
                    return ComparisonResult.SIMILAR
                }
            }
        }
        outcome
    }
}
