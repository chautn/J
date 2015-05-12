package org.exoplatform.samples.mongodbcrud;

//import

public class Executor {
	//
	public static void main(String[] args) throws Exception {
		//
		System.out.println("Hello!");
		//
		Document doc = new Document("Texas", "This is a state of America");
		DocumentService documentService = new DocumentService();
		documentService.save(doc);
		documentService.close();
		//
		System.out.println("Goodbye!");
	}
}
