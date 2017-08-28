package feature.webservice.mock

import org.yaml.snakeyaml.Yaml
import webservices.mock.MockService

import static cucumber.api.groovy.Hooks.World

/**
 * Created by zhangd on 28/08/2017.
 * World hook for MockService
 */

World {
    Yaml yaml = new Yaml()
    new MockService(yaml)
}
