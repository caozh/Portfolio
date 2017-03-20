/*
Author:      Zhihao Cao
Last modify: 11/29/2016
Title:       Program#3 Huffman encoding
Description: A simple program to perform Huffman encoding. 
Input:       A text file with each line giving the character and frequency of each character -> "a 8"
Output:      A text file with each line giving the character and the Huffman code            -> "a 1001"

Copyright 2016 Indiana University Purdue University Indianapolis. All rights reserved.
*/

#include <iostream> 
#include <vector>
#include <algorithm>    // std::sort
#include <fstream> 
#include <string>
#include <iomanip>
#include <sstream>
#include <iterator>

using namespace std;

class Node {
public:
	string name;
	int frequency;
	Node *left_child;
	Node *right_child;
	Node *parent;
	Node(string a, int b);
	void set_child(Node *c, Node *d);
	void set_parent(Node *p);
};

Node::Node(string a, int b){
	name = a;
	frequency = b;
	left_child = NULL;
	right_child = NULL;
	parent = NULL;
};

void Node::set_child(Node *c, Node *d){
	left_child = c;
	right_child = d;
};

void Node::set_parent(Node *p){
	parent = p;
};

struct less_than_key{
	inline bool operator() (const Node& struct1, const Node& struct2){
		return (struct1.frequency < struct2.frequency);
	}
};

struct less_than_key_ascii {
	inline bool operator() (const string& struct1, const string& struct2) {
		//detect "LF" -> Line Feed and convert into correct ascii code
		if ((int) struct1[0] == 76 && (int) struct1[1] == 70) {
			//cout << struct1[0] << " " << struct1[1] << ": " << (int) struct1[0] << " " << (int) struct1[1] << " struct 1" << endl;
			return (((int)struct1[0] - 66) < (int) struct2[0]);
		}
		else if ((int)struct2[0] == 76 && (int)struct2[1] == 70) {
			//cout << struct2[0] << " " << struct2[1] << ": " << (int)struct2[0] << " " << (int)struct2[1] << " struct 2" << endl;
			return (((int)struct1[0]) < (int)struct2[0] - 66);
		}
		else
			return ((int) struct1[0] < (int) struct2[0]);
	}
};

vector<string> split(const string &s, char delim) {
	stringstream ss(s);
	string item;
	vector<string> tokens;
	while (getline(ss, item, delim)) {
		tokens.push_back(item);
	}
	return tokens;
}

vector<string> loadFile(string fineName){
	vector<string> vec;           //initialize vector
	string line;
	ifstream file("freq.txt");
	if (file.is_open()) {
		while (!file.eof()) {
			getline(file, line);
			vec.push_back(line);
		}
	} else { //file could not be opened
		cout << "File could not be opened." << endl;
	}

	return vec;
}

void writeFile(string fileName, vector<string> input) {
	ofstream output_file(fileName);
	ostream_iterator<std::string> output_iterator(output_file, "\n");
	copy(input.begin(), input.end(), output_iterator);
}

vector<string> init() {
	//load freq.txt
	vector<string> vec, tmp, char_vec;            //initialize vector
	vector<int> freq_vec;
	tmp = loadFile("freq.txt");
	for (int i = 0; i < tmp.size(); i++) {
		vec = split(tmp[i], ' ');
		char_vec.push_back(vec[0]);
		freq_vec.push_back(stoi(vec[1]));
	}

	//initialization
	vector<Node> nodes;
	for (int i = 0; i < freq_vec.size(); i++) {
		nodes.push_back(Node(char_vec[i], freq_vec[i]));
	}

	cout << "size: " << nodes.size() << endl;
	int m_size = nodes.size();

	//sort the vector
	sort(nodes.begin(), nodes.end(), less_than_key());

	//we manipulate the vector "nodes", keep vector "nodes_ref" as references (deep copy)
	vector<Node> nodes_ref;
	nodes_ref.reserve(1000); //reserve the size of 1000 nodes
	for (int i = 0; i < m_size; i++) {
		nodes_ref.push_back(nodes[i]);
	}

	//loop through each chacracter within the vector
	for (int i = 0; i < m_size - 1; i++) { 
		//find the this two nodes indexes from nodes_ref
		string myString1 = nodes[0].name;
		string myString2 = nodes[1].name;
		auto it1 = find_if(nodes_ref.begin(), nodes_ref.end(), [&myString1](const Node& obj) {return obj.name == myString1; });
		auto it2 = find_if(nodes_ref.begin(), nodes_ref.end(), [&myString2](const Node& obj) {return obj.name == myString2; });
		if (it1 != nodes_ref.end() && it2 != nodes_ref.end()) { //if the element is found from nodes_ref
			//combine first two nodes
			string combine_str = nodes[0].name + nodes[1].name;
			Node internal_node = Node(combine_str, nodes[0].frequency + nodes[1].frequency);
			//cout << "combine_str: " << combine_str << endl;

			//save these two nodes' references to a new object (internal node)
			internal_node.set_child(&nodes_ref[distance(nodes_ref.begin(), it1)], &nodes_ref[distance(nodes_ref.begin(), it2)]);

			//add this internal node to vectot "nodes"
			nodes.push_back(internal_node);

			//add this internal node to vector "nodes_ref"
			nodes_ref.push_back(internal_node);

			//assign the parent node
			nodes_ref[distance(nodes_ref.begin(), it1)].set_parent(&nodes_ref.back());
			nodes_ref[distance(nodes_ref.begin(), it2)].set_parent(&nodes_ref.back());

			//remove combined nodes from vector "nodes" (the first two nodes)
			nodes.erase(nodes.begin(), nodes.begin() + 2);

			//sort the vector "nodes" (reorder the newly added internal node)
			sort(nodes.begin(), nodes.end(), less_than_key());
		}
		else {
			cout << "can't find the element from vector nodes_ref" << endl;
		}
	} // end for

	for (int i = 0; i < nodes_ref.size(); i++) {
		if (nodes_ref[i].left_child == NULL) {
			//cout << i << " " << nodes_ref[i].name << ", " << nodes_ref[i].frequency << endl;
		}
		else {
			//cout << i << " Internal node: " << nodes_ref[i].name << " " << nodes_ref[i].frequency << " (" << nodes_ref[i].left_child->frequency << ", " << nodes_ref[i].right_child->frequency << ")" << endl;
		}
	}

	//encode the leaf
	vector<string> huffman_code; //initialize output vector 
	for (int i = 0; i < nodes_ref.size(); i++){
		string this_code = ""; //"<name> <code>"
		if (nodes_ref[i].left_child == NULL) {
			Node this_child = nodes_ref[i];
			while (this_child.parent != NULL) {
				//cout << "This parent name: " << this_child.parent->name << endl; //show its parent
				//load its parent into an object
				string parent_name_str = this_child.parent->name;
				auto it_ = find_if(nodes_ref.begin(), nodes_ref.end(), [&parent_name_str](const Node& obj) {return obj.name == parent_name_str; });
				Node this_parent = nodes_ref[distance(nodes_ref.begin(), it_)];
				//left child or right child
				if (this_parent.left_child->name == this_child.name) {
					this_code += "0";
				} 
				else if (this_parent.right_child->name == this_child.name) {
					this_code += "1";
				} 
				else {
					cout << "wrong parent!" << endl;
				}
				this_child = this_parent;
			}
			//reverse the order (from root to leaf)
			reverse(this_code.begin(), this_code.end());
			//append the character
			this_code = nodes_ref[i].name + " " + this_code;

			huffman_code.push_back(this_code);
			cout << this_code << endl;
		}
		else {
			//cout << "left child is not NULL " << nodes_ref[i].name << endl;
		}
	}
	return huffman_code;
};

int main() {
	//perform huffman code encoding
	vector<string> huffman_code = init();

	//sort by ascii value
	sort(huffman_code.begin(), huffman_code.end(), less_than_key_ascii());

	//write the file
	writeFile("codetable.txt", huffman_code);

	system("pause");
	return(0);
}