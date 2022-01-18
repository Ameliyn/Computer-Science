def standard1(sentence):
    longestIndex = 0;
    i = 0;
    sentenceList = sentence.split();
    print(sentenceList);
    while i < len(sentenceList):
        if(len(sentenceList[i]) > len(sentenceList[longestIndex])):
            longestIndex = i;
        i+=1;
    print(sentenceList[longestIndex]);


print(standard1("Hello Worlds!"));
print(standard1("A Be See Deed"));
