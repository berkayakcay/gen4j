/**
 *
 * @author Berkay
 */
package gen4j.util;

import java.util.Random;


public class DNA {

    // The genetic sequence
    char[] genes;
    
    float fitness;
    
    // Constructor (makes a random DNA)
    DNA(int num) {
        genes = new char[num];
        Random randomChar = new Random();
        for (int i = 0; i < genes.length; i++) {
            genes[i] = (char)(randomChar.nextInt(122 - 32) + 32); // Pick from range of chars
        }
    }

    public void fitness(String target) {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }
        
        fitness = (float)score / (float)target.length();
    }

    public DNA crossover(DNA partner) {
        // A new child
        DNA child = new DNA(genes.length);
        
        Random randomMidPoint = new Random();
        int midpoint = randomMidPoint.nextInt(genes.length);
        
        // Half from one, half from the other
        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) {
                child.genes[i] = genes[i];
            }
            else{
                child.genes[i] = partner.genes[i];
            }
        }
        
        return child;
    }

    // Based on a mutation probability
    public void mutate(float mutationRate) {
        Random randomMutation = new Random();
        float rm = randomMutation.nextFloat();
        Random randomChar = new Random();
        for (int i = 0; i < genes.length; i++) {
            if (rm < mutationRate) {
                genes[i] = (char)(randomChar.nextInt(122 - 32) + 32);
            }
        }
    } 

    public String getPhrase() {
        return new String(genes);
    }
}
