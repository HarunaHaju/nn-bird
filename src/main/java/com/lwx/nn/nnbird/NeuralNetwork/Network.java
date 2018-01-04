package com.lwx.nn.nnbird.NeuralNetwork;

import java.util.Vector;

public class Network {
    private Layer layer0;
    private Layer layer1;

    private int layer0NeuronNum;
    private int layer1NeuronNum;
    private int neuralNum;

    private Vector<Double> inputs;

    public Network(){
        layer0 = new Layer(0,6,2);
        layer1 = new Layer(1,0,0);
        layer0NeuronNum = layer0.getNeuronNum();
        layer1NeuronNum = layer1.getNeuronNum();
        neuralNum = layer0NeuronNum+layer1NeuronNum;
        inputs = new Vector<>();
    }

    public Layer getLayer0() {
        return layer0;
    }

    public Layer getLayer1() {
        return layer1;
    }

    public int getLayer0NeuronNum() {
        return layer0NeuronNum;
    }

    public int getLayer1NeuronNum() {
        return layer1NeuronNum;
    }

    public void setLayer0(Layer layer0) {
        this.layer0 = layer0;
    }

    public void setLayer1(Layer layer1) {
        this.layer1 = layer1;
    }

    public int getNeuronNum() {
        return neuralNum;
    }

    public double getOutput(double input_1, double input_2){
        inputs.clear();
        inputs.add(input_1);
        inputs.add(input_2);
        //return layer1.getOutput(layer0.getOutput(inputs),1);
        return layer0.getOutput(inputs,0);
    }

    public void setNeuron(int layer,int neuronIndex,int index,double w,double bias){
        switch (layer){
            case 0:
                layer0.setNeuron(neuronIndex,index,w,bias);
                break;
            case 1:
                layer1.setNeuron(neuronIndex,index,w,bias);
                break;
            default:break;
        }
    }

    public void setNeuron(int layer,int neuronIndex,Vector<Double> w,double bias){
        switch (layer){
            case 0:
                layer0.setNeuron(neuronIndex,w,bias);
                break;
            case 1:
                layer1.setNeuron(neuronIndex,w,bias);
                break;
            default:break;
        }
    }

    public void setNeuron(int neuronIndex,int index,double w,double bias){
        if(neuronIndex >= layer0NeuronNum){
            layer1.setNeuron(neuronIndex - layer0NeuronNum,index,w,bias);
        }else{
            layer0.setNeuron(neuronIndex,index,w,bias);
        }
    }

    public void setNeuron(int neuronIndex,Vector<Double> w,double bias){
        if(neuronIndex >= layer0NeuronNum){
            layer1.setNeuron(neuronIndex - layer0NeuronNum,w,bias);
        }else{
            layer0.setNeuron(neuronIndex,w,bias);
        }
    }

    public Neuron getNeuron(int index){
        if(index >= layer0NeuronNum){
            return layer1.getNeural(index - layer0NeuronNum);
        }else{
            return layer0.getNeural(index);
        }
    }
}
