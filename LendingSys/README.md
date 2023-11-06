
## Building
The build must pass by running console command:  
`./gradlew build`  
Code Quality Test Report
```
CodeQualityTests > codeQuality() STANDARD_OUT
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/utils/TimeManager.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/utils/FileUtil.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/controller/MemberController.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/controller/App.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/controller/ContractController.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/controller/ItemController.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/model/Contract.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/model/MenuItem.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/model/Member.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/model/Item.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/view/ConsoleView.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/view/Menu.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/interfaces/SearchStrategy.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/strategy/SearchByCategoryStrategy.java
    0 CheckStyle Issues in /Users/zoe/Downloads/ooad/a2/app/src/main/java/strategy/SearchByNameStartsWithStrategy.java
    0 FindBugs Issues in model/Contract.java
    0 FindBugs Issues in utils/FileUtil.java
    0 FindBugs Issues in strategy/SearchByNameStartsWithStrategy.java
    0 FindBugs Issues in utils/TimeManager.java
    0 FindBugs Issues in controller/MemberController.java
    0 FindBugs Issues in model/Member.java
    0 FindBugs Issues in strategy/SearchByCategoryStrategy.java
    0 FindBugs Issues in controller/App.java
    0 FindBugs Issues in view/Menu.java
    0 FindBugs Issues in model/MenuItem.java
    2 FindBugs Issues in controller/ContractController.java
    text:lines: 22-280 
    new controller.ContractController(ConsoleView, MemberController) may expose internal representation 
      by storing an externally mutable object into ContractController.memberController 
    May expose internal representation by incorporating reference to mutable object 
    This code stores a reference to an externally mutable object into the internal representation of the 
      object. If instances are accessed by untrusted code, and unchecked changes to the mutable object 
      would compromise security or other important properties, you will need to do something different. 
      Storing a copy of the object is better approach in many situations.

    text:lines: 22-280 
    loadContractList(List) may expose internal representation by storing an externally mutable object 
      into ContractController.contractList 
    May expose internal representation by incorporating reference to mutable object 
    This code stores a reference to an externally mutable object into the internal representation of the 
      object. If instances are accessed by untrusted code, and unchecked changes to the mutable object 
      would compromise security or other important properties, you will need to do something different. 
      Storing a copy of the object is better approach in many situations.

    0 FindBugs Issues in model/Item.java
    0 FindBugs Issues in view/ConsoleView.java
    0 FindBugs Issues in interfaces/SearchStrategy.java
    2 FindBugs Issues in controller/ItemController.java
    text:lines: 26-258 
    new controller.ItemController(ConsoleView, ContractController) may expose internal representation by 
      storing an externally mutable object into ItemController.contractController 
    May expose internal representation by incorporating reference to mutable object 
    This code stores a reference to an externally mutable object into the internal representation of the 
      object. If instances are accessed by untrusted code, and unchecked changes to the mutable object 
      would compromise security or other important properties, you will need to do something different. 
      Storing a copy of the object is better approach in many situations.

    text:lines: 26-258 
    loadItemList(List) may expose internal representation by storing an externally mutable object into 
      ItemController.itemList 
    May expose internal representation by incorporating reference to mutable object 
    This code stores a reference to an externally mutable object into the internal representation of the 
      object. If instances are accessed by untrusted code, and unchecked changes to the mutable object 
      would compromise security or other important properties, you will need to do something different. 
      Storing a copy of the object is better approach in many situations.

```

Removing or manipulating the code quality checks results in an immediate assignment **Fail**. 

## Running
The application should start by running console command:  
`./gradlew run -q --console=plain`

## Adding Your Own Code
The `Simple` classes project should likely be removed do not forget to also remove the test case associated to `model.Simple`.  

Add your own code to the packages respectively and feel free to add automatic test cases for your own code. A good process is to design a little - code a little - test a little one feature at a time and then iterate.

## Running Tips

The extension `Gradle for Java` needs to be downloaded in vscode in order to use the file saving function.

## Versioning

Adhere to the git versioning instructions according to the assignment.
Zuo Ling: git version 2.37.1 (Apple Git-137.1)

## System test
Adhere to the instructions according to the assigment.

testreport.md will show all the test result

## Handing In

1. Simple authentication is made.

2. A simple selection is made using Strategy Pattern. Two ways to search: Search by Name & Search by Category.

3. Able to load and save to a json file.
