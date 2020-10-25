package de.hhu;

import java.util.logging.Logger;

public class InfiniBandDataCollectionServer {
    public static void main(String[] args) {
        // TODO make paramters check nice
        // standard port is 8000
        int port = 8000;
        if (args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        Server server = new Server(port, logger);
        server.start();

    }
}
