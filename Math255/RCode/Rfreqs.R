generate_frequency <- function(){
  
  
  numpoints = 1000
  lowerLimit = 1
  upperLimit = 100
  
  for(i in seq.int(1,10,1)){
    #Generate random numbers
    dataset = sample(lowerLimit:upperLimit, numpoints, replace = TRUE)
    
    #count frequencies
    freqs = c(0,0,0,0,0,0,0,0,0,0)
    for(q in seq.int(1,numpoints,1)){
      index = (as.integer(dataset[q] / 10))%%10 + 1
      freqs[index] = freqs[index] + 1
    }
    
    begi <- paste("R",i,"_",numpoints,"_",lowerLimit,"_",upperLimit," <- c(", sep = "")
    cat(begi, file = "Rset.R", append=TRUE, sep = "")
    write(freqs, file = "Rset.R", append=TRUE, sep = ",", ncolumns = 15)
    cat(")\n", file = "Rset.R", append=TRUE)
  }
}
