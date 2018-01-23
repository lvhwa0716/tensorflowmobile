#ifndef CCDEMO_H_
#define CCDEMO_H_

#include "tensorflow/core/public/session.h"
#include "tensorflow/core/platform/env.h"

using namespace tensorflow;

namespace demo_model {

	class DemoAdapter  {
		public:

			DemoAdapter();

			~DemoAdapter();

			void assign(std::string tname, std::vector<float>*) ; // (tensor_name, tensor)
			std::vector<std::pair<std::string, tensorflow::Tensor> > input;
	};

	class DemoLoader {
		public:
			DemoLoader();

			~DemoLoader();

			int load(tensorflow::Session*, const std::string) ;    //Load graph file and new session

			int predict(tensorflow::Session*, const DemoAdapter&, const std::string, std::vector<float>&);
			tensorflow::GraphDef graphdef; //Graph Definition for current model
	};

}

#endif /* CCDEMO_H_ */
