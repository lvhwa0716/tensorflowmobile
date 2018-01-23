


#include <iostream>
#include "tensorflow/core/public/session.h"
#include "tensorflow/core/platform/env.h"

#include "CCDemo.h"

using namespace tensorflow;

int main(int argc, char*argv) {

    std::string model_path = "../src/mobile-model.pb";

    // TensorName pre-defined in python file, Need to extract values from tensors
    std::string input_tensor_name = "in_tensor";
    std::string output_tensor_name = "out_tensor";

    // Create New Session
    Session* session;
    Status status = NewSession(SessionOptions(), &session);
    if (!status.ok()) {
        std::cout << status.ToString() << "\n";
        return 0;
    }

    // Create prediction demo
    demo_model::DemoLoader model;  //Create demo for prediction
    if (0 != model.load(session, model_path)) {
        std::cout << "Error: Model Loading failed..." << std::endl;
        return 0;
    }

    // Define Input tensor and Feature Adapter
    std::vector<float> input;
    input.push_back(1.5);
	input.push_back(2.0);

    // New Feature Adapter to convert vector to tensors dictionary
    demo_model::DemoAdapter input_feat;
    input_feat.assign(input_tensor_name, &input);   //Assign vec<double> to tensor

    // Make New Prediction
    std::vector<float> prediction;
    if (0 != model.predict(session, input_feat, output_tensor_name, prediction)) {
        std::cout << "WARNING: Prediction failed..." << std::endl;
    } else {
		std::cout << "Output Prediction Value:";
		for ( int j = 0; j < prediction.size(); j++)
		{
			std::cout << " " << prediction[j] ;
		}
		std::cout << std::endl;
	}
	return 0;
}

