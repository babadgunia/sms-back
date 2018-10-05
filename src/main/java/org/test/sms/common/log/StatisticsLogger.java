package org.test.sms.common.log;

import org.test.sms.common.utils.DateTimeUtils;
import org.test.sms.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class StatisticsLogger {

    private static ThreadLocal<StatisticsLogger> statisticsLogger = new ThreadLocal<>();

    private AppLogger logger;

    private String header;

    private List<Stage> stages = new ArrayList<>();

    private static class Stage {

        private String name;

        private long startTime;

        private Stage(String name, long startTime) {
            this.name = name;
            this.startTime = startTime;
        }
    }

    private StatisticsLogger(AppLogger logger, String header) {
        this.logger = logger;
        this.header = header;
    }

    public static void init(Class<?> clazz, String header) {
        AppLogger logger = AppLogger.getLogger(clazz);
        statisticsLogger.set(new StatisticsLogger(logger, header));
    }

    public static StatisticsLogger get() {
        return statisticsLogger.get();
    }

    public static void logMessage(String message) {
        get().logger.info(message);
    }

    public static void logStage(String stageName) {
        StatisticsLogger statLogger = get();

        statLogger.logger.info(stageName);
        statLogger.stages.add(new Stage(stageName, System.currentTimeMillis()));
    }

    public static void logSummary() {
        StatisticsLogger statLogger = get();

        if (!Utils.isBlank(statLogger.stages)) {
            long currentTime = System.currentTimeMillis();
            String underscores = Utils.expandStringPattern("-", statLogger.header.length());

            logUnderscorredMessage(statLogger, underscores, statLogger.header);

            long totalDuration = currentTime - statLogger.stages.get(0).startTime;
            int numStages = statLogger.stages.size();
            int lastIndex = numStages - 1;

            for (int i = 0; i < numStages; i++) {
                logStageSummary(statLogger, i, lastIndex, totalDuration, currentTime);
            }

            logUnderscorredMessage(statLogger, underscores, "total duration: [" + DateTimeUtils.formatDuration(totalDuration) + "]");
        }

        statisticsLogger.set(null);
    }

    private static void logUnderscorredMessage(StatisticsLogger statLogger, String underscores, String message) {
        statLogger.logger.info(underscores);
        statLogger.logger.info(message);
        statLogger.logger.info(underscores);
    }

    private static void logStageSummary(StatisticsLogger statLogger, int stageIndex, int lastIndex, long totalDuration, long currentTime) {
        long endTime;
        if (stageIndex < lastIndex) {
            int nextIndex = stageIndex + 1;
            endTime = statLogger.stages.get(nextIndex).startTime;
        } else {
            endTime = currentTime;
        }

        StringBuilder stageSummary = new StringBuilder();

        Stage stage = statLogger.stages.get(stageIndex);
        stageSummary.append("stage: ");
        stageSummary.append(stage.name);

        stageSummary.append(", duration: [");
        long duration = endTime - stage.startTime;
        stageSummary.append(DateTimeUtils.formatDuration(duration));

        stageSummary.append(" = ");

        int stagePercentage = (int) ((double) duration / totalDuration * 100);
        stageSummary.append(stagePercentage);
        stageSummary.append("%]");

        statLogger.logger.info(stageSummary.toString());
    }
}