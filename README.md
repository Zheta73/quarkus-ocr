# quarkus-ocr project

This project uses Quarkus, the Supersonic Subatomic Java Framework, to implement a REST service to extract text 
from PDF documents: 
* If the pdf file contains text, it is directly extracted using **Apache PDFBox** and **iText** libraries.
* If the pdf file contains images, the text is extracted using **Tesseract for Java** API performing an OCR.


### System requirements

The application will be deployed to a **Ubuntu 20.04.5 TLS** linux-based system.

To be able to build a **native image** the following requirements must be meet:
* **Graal VM for Java 11** must be installed


* **native-image** tool must be installed

  `$GRAALVM_HOME/bin/gu install native-image`

* Supporting native compilation in C must be enabled:

  `sudo apt-get install build-essential libz-dev zlib1g-dev`
  
* **Tesseract libraries** must be installed (visit [Tesseract 5 on Ubuntu](https://techviewleo.com/how-to-install-tesseract-ocr-on-ubuntu))


* Dependent libraries must be installed/updated:

  `sudo apt-get install libfreetype6-dev`


### Building a native application

To be able to build a native image for Ubuntu system, additional arguments must be supplied:

`sudo ./mvnw install -Dnative \`

` -Dquarkus.native.additional-build-args='--initialize-at-runtime=net.sourceforge.tess4j.TessAPI1\,org.apache.pdfbox' \`

` -Dquarkus.native.add-all-charsets=true`


### Running the application

The REST endpoint is available at:

`http://localhost:8080/api/ocr/pdf`

which expects a `multipart/form-data` request with a `file` part including the file contents to be extracted.