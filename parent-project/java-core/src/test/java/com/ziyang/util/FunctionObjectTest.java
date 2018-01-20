package com.ziyang.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.assertNotNull;

/**
 * Created by liu on 2018-01-20.
 *
 * Test class, using Function object to get rid of big switch!
 *
 * Basically, using a Map, key is the real object class, value is Function object.
 * Then, can invoke Function dynamically based on passed in object class type.
 * In this way, will NOT pollute pojo with dao.
 *
 */

//@RunWith(SpringJUnit4ClassRunner.class)
public class FunctionObjectTest {

    @Test
    public void testFunctions() throws Exception {
        DaoSample daoSample = new DaoSample();
        ServiceSample serviceSample = new ServiceSample(daoSample);
        ParentC parentC1 = new ChildC("parentC1: actual ChildC", "cid");
        System.out.println("Class: " + parentC1.getClass());
        ParentC parentC2 = new ParentC("parentC2: actual ParentC");

        List<String> results = new ArrayList<>();
//		results.add(serviceSample.getStringFromCommand("childC", parentC1));
//		results.add(serviceSample.getStringFromCommand("parentC", parentC2));
//		results.add(serviceSample.getStringFromCommand("parentC", parentC1)); // cannot(no neat way to) do magic cast

        results.add(serviceSample.getStringFromCommand(parentC1.getClass(), parentC1)); // three is magic case in commandMap
        results.add(serviceSample.getStringFromCommand(parentC2.getClass(), parentC2));

        results.add(serviceSample.getStringFromFunction(parentC1.getClass(), parentC1)); // three is magic case in commandMap
        results.add(serviceSample.getStringFromFunction(parentC2.getClass(), parentC2));

        for (int i = 0; i < results.size(); i++) {
            System.out.println("" + i + ": " + results.get(i));
        }

        assertNotNull(daoSample);
    }

    public interface Command {
        String getStringCommand(ParentC parentC);
    }

    private static class ParentC {
        public String acctNum;

        public ParentC(String acctNum) {
            this.acctNum = acctNum;
        }
    }

    private static class ChildC extends ParentC {
        public String clientId;

        public ChildC(String acctNum, String clientId) {
            super(acctNum);
            this.clientId = clientId;
        }
    }

    private class DaoSample {
        public String getString(ParentC parentC) {
            String retVal = parentC.acctNum;
            System.out.println("in getString: ParentC " + retVal);
            return retVal;
        }

        public String getString(ChildC childC) {
            String retVal = childC.acctNum;
            System.out.println("in getString: ChildC " + retVal);
            return retVal;
        }
    }

	/* another normal way ...
    NOTE: Or if you really need to pass arguments (because they represent the work state and not the algorithm state),
	then you should just do so and call it "strategy pattern" instead.

	public interface Command {
		void execute();
	}

	public class ConcreteCommand implements Command {

		private Object something;

		public ConcreteCommand(Object something) {
			this.something = something;
		}

		@Override
		public void execute() {
			// ...
		}

	}
	*/

    private class ServiceSample {
        private Map<Class, Command> commandMap;
        private Map<Class, Function> functionMap;
        private DaoSample daoSample;

        public ServiceSample(DaoSample daoSample) {
            this.daoSample = daoSample;
            initialThis();
        }

//		public void initialThis() {
//			final Map<String, Command> tempCommandMap = new HashMap<>();
//			tempCommandMap.put("childC", (ParentC parentC) -> {
//				if (parentC instanceof ChildC) {
//					return daoSample.getString((ChildC) parentC);
//				}
//				return null;
//			});
//			tempCommandMap.put("parentC", parentC -> daoSample.getString(parentC));
//			commandMap = Collections.unmodifiableMap(tempCommandMap);
//		}

//		public String getStringFromCommand(String commandType, ParentC parentC) {
//			Command command = commandMap.get(commandType);
//			if (command == null) {
//				throw new IllegalArgumentException("Invalid command type: "
//					+ commandType);
//			}
//
//			return command.getStringCommand(parentC);
//		}


        public void initialThis() {
            final Map<Class, Command> tempCommandMap = new HashMap<>();
            tempCommandMap.put(ChildC.class, childC -> daoSample.getString((ChildC) childC));
            tempCommandMap.put(ParentC.class, parentC -> daoSample.getString(parentC));
            commandMap = Collections.unmodifiableMap(tempCommandMap);

            final Map<Class, Function<ParentC, String>> tempFunctionMap = new HashMap<>();
            tempFunctionMap.put(ChildC.class, childC -> daoSample.getString((ChildC) childC));
            tempFunctionMap.put(ParentC.class, parentC -> daoSample.getString(parentC));
            functionMap = Collections.unmodifiableMap(tempFunctionMap);
        }

        public String getStringFromCommand(Class classKey, ParentC parentC) {
            Command command = commandMap.get(classKey);
            if (command == null) {
                throw new IllegalArgumentException("Invalid command type: "
                        + classKey);
            }

            return command.getStringCommand(parentC);
        }

        public String getStringFromFunction(Class classKey, ParentC parentC) {
            Function<ParentC, String> function = functionMap.get(classKey);
            if (function == null) {
                throw new IllegalArgumentException("Invalid function type: "
                        + classKey);
            }

            return function.apply(parentC);
        }
    }

}
