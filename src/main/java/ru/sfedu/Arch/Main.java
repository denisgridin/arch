package ru.sfedu.Arch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Arch.utils.Messages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            List params = Arrays.asList(args);
            if (params.size() > 0) {
                Enums.LAB lab = Enums.LAB.valueOf((String) params.get(0));
                String method = (String) params.get(1);

                HashMap arguments = parseParameters(params);

                Runner runner = new Runner();
                runner.run(lab, method, arguments);
            } else {
                log.error(Messages.ERROR_PARSE_ARGUMENTS);
            }
        } catch (Error error) {
            log.error(error);
            log.error(Messages.ERROR_PARSE_ARGUMENTS);
        }
    }


    private static HashMap<String, String> parseParameters (List args) {
        try {
            HashMap params = new HashMap();
            args.stream().forEach(el -> {
                if (el.toString().indexOf(Constants.EQUALS_SIGN) >= 1) {
                    List <String> items = Arrays.asList(el.toString().split(Constants.EQUALS_SIGN));
                    log.debug(String.format(Constants.PARAM_SET, items.get(0), items.get(1)));
                    params.put(items.get(0), items.get(1));
                }
            });
            log.debug(Constants.PARAMS + params);
            return params;
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error(Messages.CMD_PARAMS_GET);
            return null;
        }
    }
}
