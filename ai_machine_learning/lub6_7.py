#Lubanovic 6-7: Loops, Tuples, and Lists

def standard1(sentence):
    longestIndex = 0;
    i = 0;
    sentenceList = sentence.split();
    #print(sentenceList);
    while i < len(sentenceList):
        if(len(sentenceList[i]) > len(sentenceList[longestIndex])):
            longestIndex = i;
        i+=1;
    #print("Longest index is " + str(longestIndex));
    print(sentenceList[longestIndex]);

def standard2():
  i = 99;
  while i > 1:
    print(str(i) + " bottles of beer on te wall\n" + str(i) + " bottles of beer")
    print("Take one down, pass it around")
    print(str(i-1) + " bottles of beer on the wall\n")
    i-=1;
  print("1 bottle of beer on the wall\n1 bottle of beer")
  print("Take one down, pass it around")
  print("0 bottles of beer on the wall!\n")

def advanced1(sentence):
  sentenceList = sentence.split();
  print("Inital sentence:")
  print(sentenceList)
  n = len(sentenceList)
  for i in range(n-1):
    for j in range(0, n-i-1):
      if len(sentenceList[j]) > len(sentenceList[j+1]) or (len(sentenceList[j]) == len(sentenceList[j+1]) and sentenceList[j][0] > sentenceList[j+1][0]):
        temp = sentenceList[j+1]
        sentenceList[j+1] = sentenceList[j]
        sentenceList[j] = temp

  print("Sorted List:")  
  print(sentenceList)

def advanced2():
  #display roman numerals
  ones = ["","I","II","III","IV","V","VI","VII","VIII","IX"];
  tens = ["","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"];
  hundreds = ["","C"];
  for i in range(101):
    result = hundreds[(i % 1000) // 100] + tens[(i % 100) // 10] + ones[i % 10];
    print(result);

def advanced3(nest):
  #solve for nested lists
  print(nest)

#Test the functions!
#standard1("Hello World!");
#standard1("A Be Seee Dee");
#standard1("Here is a totally random sentence!")

#standard2()

#advanced1("B A totally random sentence with lots of words")

advanced2()