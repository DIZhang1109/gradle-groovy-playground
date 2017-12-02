package stepdefs.hook

import sftp.SFTPService

import static cucumber.api.groovy.Hooks.World

/**
 * Created by Di on 11/11/17.
 * World hook for SFTPService
 */
World {
    new SFTPService()
}