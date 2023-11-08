(defn hello []
                          (println "Hello Coding Challenge World"))
                        
                        (defn doublen [n]
                          (* n 2))
                        
                        (defn fib [n]
                          (if (< n 2)
                            n
                            (+ (fib (- n 1))
                               (fib (- n 2)))))
                        
                        (defn fact [n]
                          (if (<= n 1)
                            1
                            (* n (fact (- n 1)))))
                        
                        (hello)
                        
                        (println (format "The double of 5 is %d" (doublen 5)))
                        
                        (println (format "Factorial of 5 is %d" (fact 5)))
                        
                        (println (format "The 7th number of the Fibonacci sequence is %d" (fib 7)))