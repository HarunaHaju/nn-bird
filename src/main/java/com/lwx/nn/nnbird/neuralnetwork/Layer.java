package com.lwx.nn.nnbird.neuralnetwork;

import java.util.ArrayList;
import java.util.Vector;

public class Layer {
    private ArrayList<Neuron> neurons;
    private int neuronNum;
    private int layerNum;
    private int inputNum;

    public Layer(int layerNum, int neuralNum, int inputNum) {
        this.layerNum = layerNum;
        this.neuronNum = neuralNum;
        this.inputNum = inputNum;
        neurons = new ArrayList<>();
        for (int i = 0; i < neuralNum; i++) {
            Neuron neural = new Neuron(inputNum);
            this.neurons.add(neural);
        }
    }

    //the last layer output one number
    public double getOutput(Vector<Double> inputs, int flag) {
        double result = 0;
        for (int i = 0; i < neuronNum; i++) {
            result += neurons.get(i).getOutput(inputs);
        }
        return result / neuronNum;
    }

    public Vector<Double> getOutput(Vector<Double> inputs) {
        if (inputs.size() != inputNum)
            return null;
        Vector<Double> results = new Vector<>();
        for (int i = 0; i < neuronNum; i++) {
            results.add(this.neurons.get(i).getOutput(inputs));
        }
        return results;
    }

    public ArrayList<Neuron> getNeurals() {
        return neurons;
    }

    public int getLayerNum() {
        return layerNum;
    }

    public int getNeuronNum() {
        return neuronNum;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        if (neurons.size() != this.neurons.size()) {
            return;
        }
        this.neurons = neurons;
    }

    public void setLayerNum(int layerNum) {
        this.layerNum = layerNum;
    }

    public void setNeuralNum(int neuralNum) {
        this.neuronNum = neuralNum;
    }

    public Neuron getNeural(int index) {
        return this.neurons.get(index);
    }
}
