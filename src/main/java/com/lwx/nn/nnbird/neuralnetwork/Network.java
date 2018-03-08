package com.lwx.nn.nnbird.neuralnetwork;

import java.util.Vector;

public class Network {
    private Layer layer0;
    private Layer layer1;

    private int layer0NeuronNum;
    private int layer1NeuronNum;

    private Vector<Double> inputs;

    public Network() {
        layer0 = new Layer(0, 5, 2);
        layer1 = new Layer(1, 5, 5);
        layer0NeuronNum = layer0.getNeuronNum();
        layer1NeuronNum = layer1.getNeuronNum();
        inputs = new Vector<>();
    }

    public double getOutput(double input_1, double input_2) {
        inputs.clear();
        inputs.add(input_1);
        inputs.add(input_2);
        return layer1.getOutput(layer0.getOutput(inputs), 0);
    }

    public void crossover(Network network) {
        for (int i = 0; i < network.layer0.getNeurals().size(); i++) {
            layer0.getNeurals().get(i).crossover(network.layer0.getNeurals().get(i));
        }

        for (int i = 0; i < network.layer1.getNeurals().size(); i++) {
            layer1.getNeurals().get(i).crossover(network.layer1.getNeurals().get(i));
        }
    }

    public void mutate() {
        layer0.getNeurals().forEach(Neuron::mutate);
        layer1.getNeurals().forEach(Neuron::mutate);
    }
}
