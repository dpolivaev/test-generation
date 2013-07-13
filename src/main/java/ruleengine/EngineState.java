package ruleengine;

public interface EngineState extends PropertyMap {
    Strategy currentStrategy();
}
