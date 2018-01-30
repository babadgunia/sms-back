package org.test.sms.server.misc;

public class LoggingInterceptor {

    private LoggingInterceptor() {
    }

//	@AroundInvoke
//	private Object getStatistics(InvocationContext invocationContext) throws Exception {
//		if (Objects.nonNull(StatisticsLogger.get())) {
//			return invocationContext.proceed();
//		}
//
//		Class<?> callerClass = invocationContext.getMethod().getDeclaringClass();
//		String header = getLogHeader(invocationContext, callerClass);
//
//		StatisticsLogger.init(callerClass, header);
//		StatisticsLogger.logStage("invoking " + header);
//
//		try {
//			return invocationContext.proceed();
//		} finally {
//			StatisticsLogger.logMessage("finished " + header);
//			StatisticsLogger.logSummary();
//		}
//	}
//
//	private static String getLogHeader(InvocationContext invocationContext, Class<?> callerClass) {
//		StringBuilder header = new StringBuilder();
//		header.append("[");
//
//		Method callerMethod = invocationContext.getMethod();
//		header.append(callerMethod.getName());
//
//		header.append("] [");
//		header.append(callerClass.getName());
//		header.append("] [");
//
//		Parameter[] parameters = callerMethod.getParameters();
//		int numParameters = parameters.length;
//		for (int i = 0; i < numParameters; i++) {
//			Parameter parameter = parameters[i];
//
//			header.append(parameter.getType().getSimpleName());
//			header.append(" ");
//			header.append(parameter.getName());
//			header.append((i == numParameters - 1) ? "" : ", ");
//		}
//
//		header.append("]");
//
//		return header.toString();
//	}
}