/**
 *
 * @author Berkay
 */
package gen4j.util;

import java.util.ArrayList;
import java.util.Random;

public class Population {

    float mutationRate;
    DNA[] population;
    ArrayList<DNA> matingPool;
    String target;
    int generations;
    boolean finished;
    int perfectScore;

    public Population(String target, float mutationRate, int num) {
        this.target = target;
        this.mutationRate = mutationRate;
        population = new DNA[num];

        for (int i = 0; i < population.length; i++) {
            population[i] = new DNA(target.length());
        }

        calcFitness();

        matingPool = new ArrayList<DNA>();
        finished = false;
        generations = 0;

        perfectScore = 1;

    }

    // Fill our fitness array with a value for every number of the population
    public void calcFitness() {
        for (int i = 0; i < population.length; i++) {
            population[i].fitness(target);
        }
    }

    // Generate a mating pool
    public void naturalSelection() {
        // Clear the ArrayList
        matingPool.clear();

        float maxFitness = 0;
        for (int i = 0; i < population.length; i++) {
            if (population[i].fitness > maxFitness) {
                maxFitness = population[i].fitness;
            }
        }

        // Based on fitness, each member will get added to the mating pool a certain number of times
        // a higher fitness = more entries to mating pool = more likely to be picked as a parent
        // a lower fitness = fewer entries to mating pool = less likely to be picked as a parent
        for (int i = 0; i < population.length; i++) {
            float fitnes = (population[i].fitness - 0) / (maxFitness - 0);
            int n = (int) (fitnes * 100);
            for (int j = 0; j < n; j++) {
                matingPool.add(population[i]);
            }
        }
    }

    // Create a new generation
    public void generate() {
        Random randomSize = new Random();
        // Refill the population with children from the mating pool
        for (int i = 0; i < population.length; i++) {
            int a = (int) randomSize.nextInt(matingPool.size());
            int b = (int) randomSize.nextInt(matingPool.size());

            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);

            DNA child = partnerA.crossover(partnerB);
            child.mutate(mutationRate);
            population[i] = child;
        }
        generations++;
    }

    // Compute the current "most fit" member of the population
    public String getBest() {
        float worldRecord = (float) 0.0;
        int index = 0;
        for (int i = 0; i < population.length; i++) {
            if (population[i].fitness > worldRecord) {
                index = i;
                worldRecord = population[i].fitness;
            }
        }
        if (worldRecord == perfectScore) {
            finished = true;
        }
        return population[index].getPhrase();
    }

    public boolean finished() {
        return finished;
    }

    public int getGenerations() {
        return generations;
    }

    // Compute average fitness for the population
    public float getAverageFitness() {
        float total = 0;
        for (int i = 0; i < population.length; i++) {
            total += population[i].fitness;
        }
        return total / population.length;
    }

    public String allPhrases() {
        String everything = "";
        int displayLimit = Integer.min(population.length, 50);

        for (int i = 0; i < displayLimit; i++) {
            everything += population[i].getPhrase() + "\n";
        }

        return everything;
    }

}
