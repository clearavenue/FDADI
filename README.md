#myMedications Application

URL: [https://agile.clearavenue.com/FDADI](https://agile.clearavenue.com/FDADI)

Above is the publically-available URL of our prototype.

**Brief description**

Evidence of our approach, tools, infrastructure and related information can be found in the documents below:

1. [myMedications (FDADI) Tech Approach](https://github.com/clearavenue/FDADI/blob/master/documentation/myMedications\ (FDADI)\ Tech Approach.docx)
2. Agile Development Processes for myMedications (FDADI) Application and related documents

a. Our team assigned Dr. Srini Kankanahalli as our leader. As Chief Technical Officer (CTO) of clearAvenue, Dr. Kankanahalli has full authority to authorize required resources to develop the myMedications application. Additionally, Dr. Kankanahalli, a certified scrum master, is directly responsible for assuring the quality of the prototype. See Agile Development Processes for myMedications (FDADI) Application, Section 1.1 and Section 1.2, Page 3,4

b. Our team was comprised of the following:
1. Product Owner
2. Scrum Master
3. Technical Architect
4. Usability Tester
5. Front End Web Developer
6. Back End Web Developer
7. DevOps Engineer
8. Security and Engineer

See Agile Development Processes for myMedications (FDADI) Application, Section 1.2 Exhibit 1.2-1.

c. When developing myMedications application, we have utilized a number of modern, open source technologies including:

+ Bootstrap
+ jQuery
+ Apache Tomcat
+ MongoDB
+ Morphia
+ JUnit
+ FindBugs
+ Spring Web model-view-controller (MVC) framework
+ Selenium
+ Nagios
+ Eclipse
+ Java
+ Apache Maven
+ Simple Logging Facade for Java (SLF4J)
+ Nessus Vulnerability Scanner

See myMedications (FDADI) Tech Approach Section 4.0, Exhibit 4.0-1, Page 4

d. Deployed software in the AWS Instance (IaaS). See myMedications (FDADI) Tech Approach Section 6.0, Exhibit 6.0-1, Page 5

e. When developing myMedications, all code was thoroughly unit tested prior to integration into the application. Our unit test scripts were created from our functional requirements which were User Stories captured in JIRA. Each user stories also had relevant acceptance criteria which were used to create our test cases. Our test cases can be found in the Git Repository in the clearAvenue/FDADI/src/test/java/com/clearavenue/fdadi/test directory

f. We used the Bamboo Continuous Integration Server to build (using Maven) the application, deploy the application, and DevOps. The Maven build also includes automated testing through integrated Junit, Clover, FindBugs and Selenium tests.  It also performs Findbugs and Clover reports for static analysis and code coverage. The code is continuously deployed on AWS (IaaS).See Agile Development Processes for myMedications (FDADI) Application, Section 4.0, Exhibit 4.0-1, Page 12.

g. When developing myMedications, we used Configuration Management (CM) tools to control versions of software and documentation. We used the GitHub repository for configuration management for all software and documentation. See Agile Development Processes for myMedications (FDADI) Application, Section 6.0, Exhibit 6.0-1, Exhibit 6.0-2, Exhibit 6.0-3, Pages 13-15.

h. clearAvenue has utilized a combination of AWS infrastructure monitoring in conjunction with Nagios Application monitoring to cover the availability aspects of myMedications. We also intend to run periodic Nessus scans to identify the vulnerabilities and remediate the vulnerabilities immediately. We have ensured that all the components used in myMedications are patched and no known vulnerabilities exist. See Agile Development Processes for myMedications (FDADI) Application, Section 6.0, Exhibit 7.0-1, Exhibit 7.0-2, Pages 15-16

i. myMedications Application is deployed on a Docker container running on AWS. The docker file is provided in the Git repository. The docker file is in clearAvenue/FDADI/Docker directory.

j. We followed Agile/Scrum processes which were divided into 7 sprints to develop software using an iterative approach. At the completion of each sprint, feedback was solicited from users in order to add features, report bugs, correct software defects, and provide feature enhancements. See Agile Development Processes for myMedications (FDADI) Application, Section 1.0, Page 3, Exhibit 1.0-1.

The JIRA export of various user stories can be found in myMedications (FDADI) User Stories (excel) spreadsheet.

k. We developed an Install Guide to create step-by-step instructions for running the prototype of other machines. For additional details: myMedications (FDADI) Application Installation Instructions. The infrastructure set up is detailed in myMedications (FDADI) Infrastructure Setup Instructions.

l.   The myMedications application is open source and is available for no cost to anyone who wishes to utilize the application.

Copyright 2015 clearAvenue, LLC

Licensed under the MIT License

[http://choosealicense.com/licenses/mit/](http://choosealicense.com/licenses/mit/)