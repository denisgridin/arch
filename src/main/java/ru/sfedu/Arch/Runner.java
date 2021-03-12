package ru.sfedu.Arch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Arch.Enums.INHERITANCE_STRATEGY;
import ru.sfedu.Arch.lab1.api.Api1;
import ru.sfedu.Arch.lab2.api.Api2;
import ru.sfedu.Arch.lab2.model.TestEntity;
import ru.sfedu.Arch.utils.Messages;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class Runner {
    public static Logger log = LogManager.getLogger(Runner.class);

    public Runner () {}

    public Result run (Enums.LAB lab, String method, HashMap args) {
        switch (lab) {
            case LAB1: {
                runFirstLab(method);
                return new Result(Enums.STATUS.success, Constants.EMPTY_VALUE);
            }
            case LAB2: {
                runSecondLab(method, args);
                return new Result(Enums.STATUS.success, Constants.EMPTY_VALUE);
            }
            case LAB3: {
                return runThirdLab(method, args);
            }
//            case LAB4: {
//                return runFourthLab(method, args);
//            }
            default: {
                log.error(Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
                return new Result(Enums.STATUS.error, Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
            }
        }
    }

//    private Result runFourthLab(String method, HashMap args) {
//        try {
//        } catch (Error error) {
//            log.error(error);
//            log.error(Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
//            return new Result(Enums.STATUS.error, Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
//        }
//    }

    public void runFirstLab (String method) {
        try {
            Enums.METHODS_LAB_1 function = Enums.METHODS_LAB_1.valueOf(method);

            Api1 api1 = new Api1();
            switch (function) {
                case getAllTables: {
                    api1.getAllTables();
                    break;
                }
                case getAllDomains: {
                    api1.getAllDomains();
                    break;
                }
                case getAllSchemas: {
                    api1.getAllSchemas();
                    break;
                }
                case getAllPrivileges: {
                    api1.getAllPrivileges();
                    break;
                }
                default: {
                    log.error(Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
                }
            }
        } catch (Error error) {
            log.error(error);
            log.error(Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
        }
    }


    private void runSecondLab (String method, HashMap args) {
        try {
            Enums.METHODS_LAB_2 function = Enums.METHODS_LAB_2.valueOf(method);

            Api2 api2 = new Api2();
            switch (function) {
                case update: {
                    api2.updateTestBean(args);
                    break;
                }
                case save: {
                    api2.saveTestEntity(args);
                    break;
                }
                case delete: {
                    api2.deleteTestBean(args);
                    break;
                }
                case getById: {
                    Optional<TestEntity> testEntity = api2.getTestBeanById(args);
                    log.info(testEntity);
                    break;
                }
                default: {
                    log.error(Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
                }
            }
        } catch (Error error) {
            log.error(error);
            log.error(Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
        }
    }

    public Result runThirdLab (String method, HashMap args) {
        try {

            String strategy = (String) args.get(Constants.FIELD_STRATEGY);
            if (strategy != null) {
                INHERITANCE_STRATEGY inheritanceStrategy = INHERITANCE_STRATEGY.valueOf(strategy);

                switch (inheritanceStrategy) {
                    case MAPPED_SUPERCLASS: {
                        return runMappedSuperClassStrategy(method, args);
                    }
                    case JOINED: {
                        return runJoinedTableStrategy(method, args);
                    }
                    case SINGLE_TABLE: {
                        return runSingleTableStrategy(method, args);
                    }
                    case TABLE_PER_CLASS: {
                        return runTablePerClassClassStrategy(method, args);
                    }
                    default: {
                        return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
                    }
                }

            } else {
                log.error(Messages.ERROR_INHERITANCE_STRATEGY_NOT_SET);
                return new Result(Enums.STATUS.error, Messages.ERROR_INHERITANCE_STRATEGY_NOT_SET);
            }
        } catch (Exception error) {
            log.error(error);
            log.error(Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
            return new Result(Enums.STATUS.error, Messages.ERROR_CANT_FIND_METHOD_OR_LAB);
        }
    }

    public Result runMappedSuperClassStrategy (String method, HashMap args) {
        try {
            Enums.METHODS_LAB_3 function = Enums.METHODS_LAB_3.valueOf(method);
            ru.sfedu.Arch.lab3.MappedSuperclass.api.Api3 api3 = new ru.sfedu.Arch.lab3.MappedSuperclass.api.Api3();
            switch (function) {
                case saveComment: {
                    return api3.buildAndSaveComment(args);
                }
                case getCommentById: {
                    return api3.getCommentById(ru.sfedu.Arch.lab3.MappedSuperclass.model.Comment.class, UUID.fromString((String) args.get(Constants.FIELD_ID)));
                }
                case updateComment: {
                    return api3.buildAndUpdateComment(args);
                }
                case deleteComment: {
                    return api3.buildAndDeleteComment(args);
                }
                default: {
                    return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
                }
            }
        } catch (Exception error) {
            log.error(error);
            log.error(Messages.ERROR_METHOD_RUN);
            return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
        }
    }


    public Result runJoinedTableStrategy (String method, HashMap args) {
        try {
            Enums.METHODS_LAB_3 function = Enums.METHODS_LAB_3.valueOf(method);
            ru.sfedu.Arch.lab3.JoinedTable.api.Api3 api3 = new ru.sfedu.Arch.lab3.JoinedTable.api.Api3();
            switch (function) {
                case saveComment: {
                    return api3.buildAndSaveComment(args);
                }
                case getCommentById: {
                    return api3.getCommentById(ru.sfedu.Arch.lab3.JoinedTable.model.Comment.class, UUID.fromString((String) args.get(Constants.FIELD_ID)));
                }
                case updateComment: {
                    return api3.buildAndUpdateComment(args);
                }
                case deleteComment: {
                    return api3.buildAndDeleteComment(args);
                }
                default: {
                    return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
                }
            }
        } catch (Exception error) {
            log.error(error);
            log.error(Messages.ERROR_METHOD_RUN);
            return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
        }
    }
    public Result runSingleTableStrategy (String method, HashMap args) {
        try {
            Enums.METHODS_LAB_3 function = Enums.METHODS_LAB_3.valueOf(method);
            ru.sfedu.Arch.lab3.SingleTable.api.Api3 api3 = new ru.sfedu.Arch.lab3.SingleTable.api.Api3();
            switch (function) {
                case saveComment: {
                    return api3.buildAndSaveComment(args);
                }
                case getCommentById: {
                    return api3.getCommentById(ru.sfedu.Arch.lab3.SingleTable.model.Comment.class, UUID.fromString((String) args.get(Constants.FIELD_ID)));
                }
                case updateComment: {
                    return api3.buildAndUpdateComment(args);
                }
                case deleteComment: {
                    return api3.buildAndDeleteComment(args);
                }
                default: {
                    return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
                }
            }
        } catch (Exception error) {
            log.error(error);
            log.error(Messages.ERROR_METHOD_RUN);
            return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
        }
    }
    public Result runTablePerClassClassStrategy (String method, HashMap args) {
        try {
            Enums.METHODS_LAB_3 function = Enums.METHODS_LAB_3.valueOf(method);
            ru.sfedu.Arch.lab3.TablePerClass.api.Api3 api3 = new ru.sfedu.Arch.lab3.TablePerClass.api.Api3();
            switch (function) {
                case saveComment: {
                    return api3.buildAndSaveComment(args);
                }
                case getCommentById: {
                    return api3.getCommentById(ru.sfedu.Arch.lab3.TablePerClass.model.Comment.class, UUID.fromString((String) args.get(Constants.FIELD_ID)));
                }
                case updateComment: {
                    return api3.buildAndUpdateComment(args);
                }
                case deleteComment: {
                    return api3.buildAndDeleteComment(args);
                }
                default: {
                    return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
                }
            }
        } catch (Exception error) {
            log.error(error);
            log.error(Messages.ERROR_METHOD_RUN);
            return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
        }
    }
}
