package ruleengine;

class StateFormatter {
    private int iterationCounter = 0;
    StringBuilder stringBuilder = new StringBuilder();

    public StateFormatter withState(String... properties) {
        stringBuilder.append(++iterationCounter).append(" : ");
        for (int i = 0; i < properties.length; i += 2) {
            if (i > 0)
                stringBuilder.append("\t");
            stringBuilder.append(properties[i + 0]).append("=")
                .append(properties[i + 1]);
        }
        stringBuilder.append("\n");
        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}