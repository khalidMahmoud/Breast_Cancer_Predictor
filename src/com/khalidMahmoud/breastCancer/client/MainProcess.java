package com.khalidMahmoud.breastCancer.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import com.khalidMahmoud.breastCancer.loadData.LoadData;

import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

public class MainProcess {

	public static String predictNewInstance(int clumpThickness, int uniformityOfCellSize, int uniformityOfCellShape,
			int marginalAdhesion, int singleEpithelialCellSize, int bareNuclei, int blandChromatin, int normalNucleoli,
			int Mitoses) throws Exception {
		String result;
		int numFolds = 10;
		RandomForest rf = new RandomForest();
		// get data file
		LoadData loadData = new LoadData();
		Instances dataset = loadData.loadDataFile();
		// classification process
		dataset.setClassIndex(dataset.numAttributes() - 1);
		rf.setNumTrees(500);
		System.out.println(rf.getNumTrees()); //
		rf.buildClassifier(dataset);
		Evaluation evaluation = new Evaluation(dataset);
		System.out.println("num tree"); //
		evaluation.crossValidateModel(rf, dataset, numFolds, new Random(1));
		IBk classifier = new IBk(5);
		classifier.buildClassifier(dataset);
		/*
		 * System.out.println(evaluation.toSummaryString("\nResults\n======\n",
		 * true)); System.out.println(evaluation.toClassDetailsString());
		 * System.out.println("Results For Class -1- "); System.out.println(
		 * "Precision=  " + evaluation.precision(0)); System.out.println(
		 * "Recall=  " + evaluation.recall(0)); System.out.println(
		 * "F-measure=  " + evaluation.fMeasure(0)); System.out.println(
		 * "Results For Class -2- "); System.out.println("Precision=  " +
		 * evaluation.precision(1)); System.out.println("Recall=  " +
		 * evaluation.recall(1)); System.out.println("F-measure=  " +
		 * evaluation.fMeasure(1));
		 */
		// predict new data that is not classified
		Instance instanceValue1 = new SparseInstance(9);
		instanceValue1.setValue(0, clumpThickness);
		instanceValue1.setValue(1, uniformityOfCellSize);
		instanceValue1.setValue(2, uniformityOfCellShape);
		instanceValue1.setValue(3, marginalAdhesion);
		instanceValue1.setValue(4, singleEpithelialCellSize);
		instanceValue1.setValue(5, bareNuclei);
		instanceValue1.setValue(6, blandChromatin);
		instanceValue1.setValue(7, normalNucleoli);
		instanceValue1.setValue(8, Mitoses);
		double[] prediction = classifier.distributionForInstance(instanceValue1);
		/*
		 * prediction[0] is benign and prediction[0] is malignant
		 */

		/*for (int i = 0; i < prediction.length; i++) {
			double d = prediction[i];
			System.out.println(d);
		}*/
		if (prediction[1] > prediction[0]) {
			result = "malignant";
		} else {
			result = "benign";
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String out=predictNewInstance(3, 1, 1, 1, 10, 1, 6, 2, 1);
		System.out.println(out);
	}
}
