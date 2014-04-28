package org.dpolivaev.tsgen.ruleengine;

import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.iterate;
import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.rule;
import static org.dpolivaev.tsgen.ruleengine.RuleBuilder.Factory.skip;
import static org.dpolivaev.tsgen.testutils.Combinations.combination;
import static org.junit.Assert.assertEquals;

import org.dpolivaev.tsgen.ruleengine.Condition;
import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.RuleEngine;
import org.dpolivaev.tsgen.ruleengine.Strategy;
import org.dpolivaev.tsgen.ruleengine.ValueProvider;
import org.dpolivaev.tsgen.testutils.CollectingScriptProducer;
import org.dpolivaev.tsgen.testutils.Combinations;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngineExamples {

    private RuleEngine ruleEngine;
    private Strategy strategy;
    private CollectingScriptProducer scriptProducerMock;
    private void generateCombinationsForStrategy() {
        ruleEngine.run(strategy);
    }

    private void expect(Combinations expectedCombinations) {
        assertEquals(expectedCombinations.toString(),
            scriptProducerMock.getAllScriptPropertyCombinations());
    }

    private void initializeRuleEngine(CollectingScriptProducer loggingScriptProducerMock) {
        scriptProducerMock = loggingScriptProducerMock;
        ruleEngine = new RuleEngine().addHandler(scriptProducerMock);
    }
    
    @Before
    public void setup() {
        CollectingScriptProducer loggingScriptProducerMock = new CollectingScriptProducer();
        initializeRuleEngine(loggingScriptProducerMock);
        strategy = new Strategy();
    }

    @Test
    public void standAloneProperty() {
//'stand alone property'
//    $x
//        :
//            'a', 'b', 'c'
        strategy.addRule(iterate("x").over("a", "b", "c"));
        generateCombinationsForStrategy();
        expect(combination("x", "a").followedBy("x", "b").followedBy("x", "c"));
    }

    @Test
    public void allCombinations1() {
//'all combinations 1'
//    $x
//        :
//            'a', 'b', 'c'
//                $y
//                    :
//                        'A', 'B', 'C'
        strategy.addRule(iterate("x").over("a", "b", "c").ordered().with(iterate("y").over("A", "B", "C").ordered()));
        generateCombinationsForStrategy();
        expect(combination("x", "a", "y", "A").followedBy("x", "a", "y", "B").followedBy("x", "a", "y", "C")
            .followedBy("x", "b", "y", "A").followedBy("x", "b", "y", "B").followedBy("x", "b", "y", "C")
            .followedBy("x", "c", "y", "A").followedBy("x", "c", "y", "B").followedBy("x", "c", "y", "C"));
    }
    @Test
    public void parallelCombinationsOrdered() {
//'parallel combinations ordered'
//    $x
//        :
//            'a', 'b', 'c'
//                ordered
//    $y
//        :
//            'A', 'B'
//                ordered
//    $z
//        :
//            '1', '2', '3', '4', '5'
//                ordered
        strategy.addRule(iterate("x").over("a", "b", "c").ordered());
        strategy.addRule(iterate("y").over("A", "B").ordered());
        strategy.addRule(iterate("z").over("1", "2", "3", "4", "5").ordered());
        generateCombinationsForStrategy();
        expect(combination("x", "a", "y", "A", "z", "1")
            .followedBy("x", "b", "y", "B", "z", "2")
            .followedBy("x", "c", "y", "A", "z", "3")
            .followedBy("x", "a", "y", "B", "z", "4")
            .followedBy("x", "b", "y", "A", "z", "5"));
    }
    @Test
    public void dependentValues() {
      //'dependent values'
//      $x
//          :
//              'a', 'b', 'c'
//                  ordered
//      $y
//          :
//              'A', 'B'
//                  ordered
//      $z
//          :
//              $x # " && " # $y ,  $x # " || " # $y
        strategy.addRule(iterate("x").over("a", "b", "c").ordered());
        strategy.addRule(iterate("y").over("A", "B").ordered());
        strategy.addRule(iterate("z").over(new ValueProvider() {
            @Override
            public Object value(PropertyContainer propertyContainer) {
                 return propertyContainer.get("x") + "&&" + propertyContainer.get("y");
            }
        },
        new ValueProvider() {
            @Override
            public Object value(PropertyContainer propertyContainer) {
                 return propertyContainer.get("x") + "||" + propertyContainer.get("y");
            }
        }).ordered());
        generateCombinationsForStrategy();
        expect(combination("x", "a", "y", "A", "z", "a&&A")
            .followedBy("x", "a", "y", "A", "z", "a||A")
            .followedBy("x", "b", "y", "B", "z", "b&&B")
            .followedBy("x", "c", "y", "A", "z", "c||A"));
    }

    
    @Test
    public void explicitDependencies() {
//'explicit dependencies'
//    when
//        $x, $y
//            $z
//                :
//                    $x # " && " # $y ,  $x # " || " # $y
//    $x
//        :
//            'a', 'b', 'c'
//    $y
//        :
//            'A', 'B'
        strategy.addRule(iterate("z").over(new ValueProvider() {
            @Override
            public Object value(PropertyContainer propertyContainer) {
                 return propertyContainer.get("x") + "&&" + propertyContainer.get("y");
            }
        },
        new ValueProvider() {
            @Override
            public Object value(PropertyContainer propertyContainer) {
                 return propertyContainer.get("x") + "||" + propertyContainer.get("y");
            }
        }).ordered().when("x", "y"));
        
        strategy.addRule(iterate("x").over("a", "b", "c").ordered());
        strategy.addRule(iterate("y").over("A", "B").ordered());
        
        generateCombinationsForStrategy();
        
        expect(combination("x", "a", "y", "A", "z", "a&&A")
            .followedBy("x", "a", "y", "A", "z", "a||A")
            .followedBy("x", "b", "y", "B", "z", "b&&B")
            .followedBy("x", "b", "y", "B", "z", "b||B")
            .followedBy("x", "c", "y", "A", "z", "c&&A")
            .followedBy("x", "c", "y", "A", "z", "c||A"));
    }
    
    @Test
    public void branches() {
//'branches'
//    $x
//        :
//            'a', 'b'
//                $y
//                    :
//                        'A', 'B'
//            'c'
//                $y
//                    :
//                        'C'
        strategy.addRule(iterate("x").over("a", "b").ordered().with(iterate("y").over("A", "B").ordered())
            .over("c").with(iterate("y").over("C")));
        generateCombinationsForStrategy();
        expect(combination("x", "a", "y", "A").followedBy("x", "a", "y", "B")
            .followedBy("x", "b", "y", "A").followedBy("x", "b", "y", "B")
            .followedBy("x", "c", "y", "C"));
    }
    
    @Test
    public void conditions() {
// 'conditions'
//      $x
//          :
//              'a', 'b'
//              'c'
//          $y
//              :
//                  'C'
//              if
//                  $x == 'c'
//                      :
//                          'A', 'B'
        strategy.addRule(iterate("x").over("a", "b", "c")
            .with(rule("y").iterate("y").over("C"),
            		rule("y").iterate("y").over("A", "B")._if(new Condition() {
                    
                    @Override
                    public boolean isSatisfied(PropertyContainer propertyContainer) {
                        return "c".equals(propertyContainer.get("x"));
                    }
                })));
        generateCombinationsForStrategy();
        expect(combination("x", "a", "y", "C")
            .followedBy("x", "b", "y", "C")
            .followedBy("x", "c", "y", "A").followedBy("x", "c", "y", "B"));
    }
    @Test
    public void defaults() {
//'defaults'
//    $x
//        :
//            'a', 'b', 'c'
//             $y1
//                 :
//                   $y
//            'd'
//                $y
//                    :
//                        'A', 'B'
//        defaults
//            $y
//                =
//                    'C'
//                if
//                    $x != 'b'
//                        =
//                            'D', 'E'
        
        strategy.addRule(iterate("x").over("a", "b", "c")
            .with(iterate("y1").over(new ValueProvider() {
                @Override
                public Object value(PropertyContainer propertyContainer) {
                    return propertyContainer.get("y");
                }
            }))
            .over("d").with(iterate("y").over("A", "B"))
        );
        
        strategy.addRule(iterate("y").over("C").asDefaultRule());
        strategy.addRule(iterate("y").over("D", "E")._if(new Condition() {
            
            @Override
            public boolean isSatisfied(PropertyContainer propertyContainer) {
                return ! "b".equals(propertyContainer.get("x"));
            }
        }).asDefaultRule());
        
        generateCombinationsForStrategy();
        expect(combination("x", "a", "y", "D", "y1", "D")
            .followedBy("x", "b", "y", "C", "y1", "C")
            .followedBy("x", "c", "y", "E", "y1", "E")
            .followedBy("x", "d", "y", "A")
            .followedBy("x", "d", "y", "B"));
    }
    @Test
    public void branchedDefaults() {
        
//'branched defaults'
//    $x
//        :
//            'a'
//            'b'
//                $y
//                    :
//                        'A'
//            'c'
//                defaults
//                    $y
//                        =
//                            'B'
//        defaults
//            $y
//                =
//                    'C'
//                if
//                    $x != 'b'
//                        =
//                            'D', 'E'
        
        strategy.addRule(iterate("x").over("a")
            .with(iterate("y'").over(new ValueProvider() {
                @Override
                public Object value(PropertyContainer propertyContainer) {
                    return propertyContainer.get("y");
                }
            }))
            .over("b").with(iterate("y").over("A"))
            .over("c").with(iterate("y").over("B").asDefaultRule(), 
                iterate("y'").over(new ValueProvider() {
				    @Override
				    public Object value(PropertyContainer propertyContainer) {
				        return propertyContainer.get("y");
				    }
				}))
        );
        
        strategy.addRule(iterate("y").over("C").asDefaultRule());
        strategy.addRule(iterate("y").over("D", "E")._if(new Condition() {
            
            @Override
            public boolean isSatisfied(PropertyContainer propertyContainer) {
                return ! "b".equals(propertyContainer.get("x"));
            }
        }).asDefaultRule());
        
        generateCombinationsForStrategy();
        expect(combination("x", "a", "y", "D", "y'", "D")
            .followedBy("x", "b", "y", "A")
            .followedBy("x", "c", "y", "B", "y'", "B"));
    }
    @Test
    public void chainedDefaults() {
//'chained defaults'
//    $x
//        :
//            'a', 'b'
//            'c'
//                $y
//                    :
//                        'A', 'B'
//        defaults
//            $y
//                =
//                    $z
//                if
//                    $z != 'b'
//                        =
//                            'D', 'E'
//            $z
//                =
//                    $x
        strategy.addRule(iterate("x").over("a", "b")
            .with(iterate("y'").over(new ValueProvider() {
                @Override
                public Object value(PropertyContainer propertyContainer) {
                    return propertyContainer.get("y");
                }
            }))
            .over("c").with(iterate("y").over("A", "B"))
        );
        
        strategy.addRule(iterate("y").over(new ValueProvider() {
            @Override
            public Object value(PropertyContainer propertyContainer) {
                return propertyContainer.get("z");
            }
        }).asDefaultRule());

        strategy.addRule(iterate("y").over("D", "E")._if(new Condition() {
            @Override
            public boolean isSatisfied(PropertyContainer propertyContainer) {
                return ! "b".equals(propertyContainer.get("z"));
            }
        }).asDefaultRule());
        
        strategy.addRule(iterate("z").over(new ValueProvider() {
            @Override
            public Object value(PropertyContainer propertyContainer) {
                return propertyContainer.get("x");
            }
        }).asDefaultRule());
        
        generateCombinationsForStrategy();
        
        expect(combination("x", "a", "z", "a", "y", "D", "y'", "D")
            .followedBy("x", "b",  "z", "b", "y", "b", "y'", "b")
            .followedBy("x", "c", "y", "A")
            .followedBy("x", "c", "y", "B"));
    }
    
    @Test
    public void skipCombination() {
//'skip'
//    $x
//        :
//            'a', 'b', 'c'
//                $y
//                    :
//                        'A', 'C'
//                    if
//                        $x == 'b'
//                            skip
        strategy.addRule(iterate("x").over("a", "b", "c").ordered()
            .with(
                iterate("y").over("A", "C").ordered(),
                skip()._if(new Condition() {
                    
                    @Override
                    public boolean isSatisfied(PropertyContainer propertyContainer) {
                        return "b".equals(propertyContainer.get("x"));
                    }
                }))
        );
       generateCombinationsForStrategy();
        
        expect(combination("x", "a", "y", "A")
            .followedBy("x", "a", "y", "C")
             .skip().skip()
            .followedBy("x", "c", "y", "A")
            .followedBy("x", "c", "y", "C"));
   }
    
    @Test
    public void ruleStack() {

//'rule stack'
//    $x
//        :
//            "a"
//    $x
//        :
//            "b"
//    $x
//        :
//            "c"
        
        strategy.addRule(rule("x").iterate("x").over("a"));
        strategy.addRule(rule("x").iterate("x").over("b"));
        strategy.addRule(rule("x").iterate("x").over("c"));
        
        generateCombinationsForStrategy();
        expect(combination("x", "c"));
        
    }
}
