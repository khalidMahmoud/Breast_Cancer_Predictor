package com.khalidMahmoud.breastCancer.loadData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class LoadData {
	public Instances loadDataFile() throws FileNotFoundException, IOException {
		/* Start loading data file */

		Instances dataset = new Instances(new BufferedReader(new FileReader("arffData.arff")));
		System.out.println(dataset.toSummaryString());
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File("newArffData.arff"));
		saver.writeBatch();
		return dataset;
		/* End loading data file */
	}
}
