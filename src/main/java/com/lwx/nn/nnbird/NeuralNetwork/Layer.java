package com.lwx.nn.nnbird.NeuralNetwork;

import java.util.Vector;

/**
 * Package: com.lwx.nn.nnbird.nn
 * Comments:
 * Author: lwx
 * Create Date: 2017/11/28
 * Modified Date: 2017/11/28
 * Why & What is modified:
 * Version: 0.0.1beta
 * It's the only NEET thing to do. – Shionji Yuuko
 */
public class Layer {
    private Vector<Neuron> neurons;
    private int neuronNum;
    private int layerNum;
    private int inputNum;

    public Layer(int layerNum ,int neuralNum,int inputNum){
        this.layerNum = layerNum;
        this.neuronNum = neuralNum;
        this.inputNum = inputNum;
        neurons = new Vector<>();
        for (int i = 0;i<neuralNum;i++){
            Neuron neural = new Neuron(inputNum);
            this.neurons.add(neural);
        }
    }

    public Vector<Neuron> getNeurals() {
        return neurons;
    }

    public int getLayerNum() {
        return layerNum;
    }

    public int getNeuronNum() {
        return neuronNum;
    }

    public void setNeurons(Vector<Neuron> neurons) {
        if(neurons.size()!=this.neurons.size()){
            return;
        }
        this.neurons = neurons;
    }

    public void setNeuron(int neuronIndex,int index,double w,double bias) {
        if(index >= neuronNum)
            return;
        this.neurons.get(neuronIndex).setW(index,w);
        this.neurons.get(index).setBias(bias);
    }

    public void setNeuron(int neuronIndex,Vector<Double> w,double bias) {
        if(w.size() != neurons.get(neuronIndex).getW().size())
            return;
        this.neurons.get(neuronIndex).setW(w);
        this.neurons.get(neuronIndex).setBias(bias);
    }

    public void setLayerNum(int layerNum) {
        this.layerNum = layerNum;
    }

    public void setNeuralNum(int neuralNum) {
        this.neuronNum = neuralNum;
    }

    public Vector<Double> getOutput(Vector<Double> inputs){
        if(inputs.size() != inputNum)
            return null;
        Vector<Double> results = new Vector<>();
        for (int i = 0; i < neuronNum; i++) {
            results.add(this.neurons.get(i).getOutput(inputs));
        }
        return results;
    }

//    public double getOutput(Vector<Double> inputs,int flag){
//        if(inputs.size() != inputNum)
//            return 0;
//        return this.neurons.get(0).getOutput(inputs) - this.neurons.get(1).getOutput(inputs);
//    }

    public double getOutput(Vector<Double> inputs,int flag){
        double result = 0;
        for (int i = 0;i<neuronNum;i++)
        {
            result += neurons.get(i).getOutput(inputs);
        }
        return result / neuronNum;
    }

    public Neuron getNeural(int index){
        return this.neurons.get(index);
    }
}
