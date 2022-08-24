package klox
import klox.www.main as ServerMain
import klox.cmd.main as CmdMain

fun main(args:Array<String>) {
    if (args.size == 1 && args.first() == "-server") {
        CmdMain(args)
    } else {
        ServerMain(args)
    }
}

fun printUsage() {
    println("Usage: [-server]")
    println()
    println(" -server\tLaunch a backend service")
}