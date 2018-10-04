@echo off
javac -classpath ".\melissadata\businesscoderuk\org.apache.sling.commons.json-2.0.20.jar;" .\melissadata\businesscoderuk\*.java .\melissadata\businesscoderuk\view\*.java ./melissadata\businesscoderuk\model\*.java
java -classpath ".\melissadata\businesscoderuk\org.apache.sling.commons.json-2.0.20.jar;"; melissadata.businesscoderuk.Main melissadata.businesscoderuk.view.BusinessCoderUKController melissadata.businesscoderuk.view.BusinessCoderUKTransactionController melissadata.businesscoderuk.view.RootLayoutController 
del .\melissadata\businesscoderuk\*.class 
del .\melissadata\businesscoderuk\view\*.class 
del .\melissadata\businesscoderuk\model\*.class