package ruleengine;

import static org.junit.Assert.assertEquals;
import static ruleengine.Combinations.combination;
import static ruleengine.StatefulRuleBuilder.Factory.iterate;
import static ruleengine.StatefulRuleBuilder.Factory.when;

import static ruleengine.ConstantValue.Instruction.SKIP;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dimitry Polivaev 18.02.2013
 */
public class RuleEngineExamples {

    private RuleEngine ruleEngine;
    private Strategy strategy;
    private LoggingScriptProducerMock scriptProducerMock;
    private void generateCombinationsForStrategy() {
        ruleEngine.run(strategy);
    }

    private void expect(Combinations expectedCombinations) {
        assertEquals(expectedCombinations.toString(),
            scriptProducerMock.getAllScriptPropertyCombinations());
    }

    private void initializeRuleEngine(LoggingScriptProducerMock loggingScriptProducerMock) {
        scriptProducerMock = loggingScriptProducerMock;
        ruleEngine = new RuleEngine(scriptProducerMock);
    }
    
    @Before
    public void setup() {
        LoggingScriptProducerMock loggingScriptProducerMock = new LoggingScriptProducerMock();
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
        strategy.addRule(iterate("x").over("a", "b", "c").with(iterate("y").over("A", "B", "C")));
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
        strategy.addRule(iterate("x").over("a", "b", "c"));
        strategy.addRule(iterate("y").over("A", "B"));
        strategy.addRule(iterate("z").over("1", "2", "3", "4", "5"));
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
        strategy.addRule(iterate("x").over("a", "b", "c"));
        strategy.addRule(iterate("y").over("A", "B"));
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
        }));
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
        }).when("x", "y"));
        
        strategy.addRule(iterate("x").over("a", "b", "c"));
        strategy.addRule(iterate("y").over("A", "B"));
        
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
        strategy.addRule(iterate("x").over("a", "b").with(iterate("y").over("A", "B"))
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
            .with(iterate("y").over("C"),
                iterate("y").over("A", "B")._if(new Condition() {
                    
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
            .with(iterate("y'").over(new ValueProvider() {
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
        expect(combination("x", "a", "y", "D", "y'", "D")
            .followedBy("x", "b", "y", "C", "y'", "C")
            .followedBy("x", "c", "y", "E", "y'", "E")
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
            }).asTriggeredRule())
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
    public void skip() {
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
        strategy.addRule(iterate("x").over("a", "b", "c")
            .with(
                iterate("y").over("A", "C"),
                iterate("y").over(SKIP)._if(new Condition() {
                    
                    @Override
                    public boolean isSatisfied(PropertyContainer propertyContainer) {
                        return "b".equals(propertyContainer.get("x"));
                    }
                }))
        );
       generateCombinationsForStrategy();
        
        expect(combination("x", "a", "y", "A")
            .followedBy("x", "a", "y", "C")
             .skip()
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
        
        strategy.addRule(iterate("x").over("a"));
        strategy.addRule(iterate("x").over("b"));
        strategy.addRule(iterate("x").over("c"));
        
        generateCombinationsForStrategy();
        expect(combination("x", "c"));
        
    }
}
