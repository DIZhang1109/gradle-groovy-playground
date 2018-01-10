package stepdefs.hook

import sftp.SftpService

import static cucumber.api.groovy.Hooks.World

/**
 * Created by Di on 11/11/17.
 * World hook for SftpService
 */
World {
    new SftpService()
}