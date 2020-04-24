# Quality metric measurement
Evaluates observability controlability dependedability of a compodent used in a communicating system  from models (IOLTS, DAGs) 

## Usage
After compilation in folder **bin**

```
java -cp bin:lib/commons-cli-1.4.jar main.Main -i <dot file> -d <folder containing the DAGs>
```
**\<dot file\>** : your input dot file.
The folder must contain all the DAGs of all the components
