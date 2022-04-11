generate_dataset <- function(){
  
  nums <- c(128,512,1024,2048);
  limitAs <- c(0,1,1,1,1);
  limitBs <- c(1,128,512,1024,2048);
  intordoubs <- c(1,0,0,0,0);
  
  for(i in c(1,2,3,4)){
    for(j in c(1,2,3,4,5)){
      numpoints = nums[i]
      limitA = limitAs[j]
      limitB = limitBs[j]
      intordoub = intordoubs[j]
      if(intordoub == 1){
        dataset = runif(numpoints, limitA, limitB)
      }
      else{
        dataset = sample(limitA:limitB, numpoints, replace = TRUE)
      }
      begi <- paste("R",numpoints,"_",limitA,"_",limitB," <- c(", sep = "")
      cat(begi, file = "Rdataset.R", append=TRUE, sep = "")
      for(q in seq.int(1,numpoints,1)){
        cat(dataset[q], file = "Rdataset.R", append=TRUE)
        if(q == numpoints){
          cat(")\n", file = "Rdataset.R", append=TRUE, sep = "")
        }
        else{
          cat(", ", file = "Rdataset.R", append=TRUE)
          if(q%%20 == 0){
            cat("\n", file = "Rdataset.R", append=TRUE)
          }
        }
      }
    }
  }
}
