###############
## whole time##
###############

rm(list = ls(all = TRUE))
setwd("C:/Users/Andre/Documents/Uni/MA/R/Input")
options("scipen"=100, "digits"=4)

data = read.csv("2015_DsegRanking_all/jams2015_all.csv",header=TRUE, colClasses=c("character",'numeric','numeric'))
# exclude data with NaN and negative values
data = data[! data$carDelay %in% c(NaN), ]
data = data[!data$carDelay<0, ]
# sort data frame by totalDelay
data = data[order(-data$carDelay),]

# extract the Top 1000 DSEGs
data.top1000 = head(data,nrow(data)*0.01)
minDelay = min(data.top1000$carDelay)
maxDelay = max(data.top1000$carDelay)
data.top1000 = data.frame(dsegID = data.top1000$DsegID, delay= data.top1000$carDelay, count=data.top1000$Count, minDelay=c(1:nrow(data.top1000)), maxDelay=c(1:nrow(data.top1000)))
data.top1000$minDelay = minDelay
data.top1000$maxDelay = maxDelay

# Output Need to be activated
setwd("C:/Users/Andre/Documents/Uni/MA/R/Output/2015_DsegRanking_all")
write.table(data.top1000, file="jams2015_all.csv", col.names=TRUE, row.names=FALSE, sep=",")

##########################
## Morning Peak Weekdays##
##########################

rm(list = ls(all = TRUE))
setwd("C:/Users/Andre/Documents/Uni/MA/R/Input")
options("scipen"=100, "digits"=4)

data = read.csv("2015_DsegRanking_all/jams2015_all_MP_WD.csv",header=TRUE, colClasses=c("character",'numeric','numeric'))
# exclude data with NaN and negative values
data = data[! data$carDelay %in% c(NaN), ]
data = data[!data$carDelay<0, ]
# sort data frame by totalDelay
data = data[order(-data$carDelay),]

# extract the Top 1000 DSEGs
data.top1000 = head(data,nrow(data)*0.01)
minDelay = min(data.top1000$carDelay)
maxDelay = max(data.top1000$carDelay)
data.top1000 = data.frame(dsegID = data.top1000$DsegID, delay= data.top1000$carDelay, count=data.top1000$Count, minDelay=c(1:nrow(data.top1000)), maxDelay=c(1:nrow(data.top1000)))
data.top1000$minDelay = minDelay
data.top1000$maxDelay = maxDelay

# Output Need to be activated
setwd("C:/Users/Andre/Documents/Uni/MA/R/Output/2015_DsegRanking_all")
write.table(data.top1000, file="jams2015_all_MP_WD.csv", col.names=TRUE, row.names=FALSE, sep=",")

##########################
## Evening Peak Weekdays##
##########################

rm(list = ls(all = TRUE))
setwd("C:/Users/Andre/Documents/Uni/MA/R/Input")
options("scipen"=100, "digits"=4)

data = read.csv("2015_DsegRanking_all/jams2015_all_EP_WD.csv",header=TRUE, colClasses=c("character",'numeric','numeric'))
# exclude data with NaN and negative values
data = data[! data$carDelay %in% c(NaN), ]
data = data[!data$carDelay<0, ]
# sort data frame by totalDelay
data = data[order(-data$carDelay),]

# extract the Top 1000 DSEGs
data.top1000 = head(data,nrow(data)*0.01)
minDelay = min(data.top1000$carDelay)
maxDelay = max(data.top1000$carDelay)
data.top1000 = data.frame(dsegID = data.top1000$DsegID, delay= data.top1000$carDelay, count=data.top1000$Count, minDelay=c(1:nrow(data.top1000)), maxDelay=c(1:nrow(data.top1000)))
data.top1000$minDelay = minDelay
data.top1000$maxDelay = maxDelay

# Output Need to be activated
setwd("C:/Users/Andre/Documents/Uni/MA/R/Output/2015_DsegRanking_all")
write.table(data.top1000, file="jams2015_all_EP_WD.csv", col.names=TRUE, row.names=FALSE, sep=",")

#########################
## Morning Peak Weekend##
#########################

rm(list = ls(all = TRUE))
setwd("C:/Users/Andre/Documents/Uni/MA/R/Input")
options("scipen"=100, "digits"=4)

data = read.csv("2015_DsegRanking_all/jams2015_all_MP_WE.csv",header=TRUE, colClasses=c("character",'numeric','numeric'))
# exclude data with NaN and negative values
data = data[! data$carDelay %in% c(NaN), ]
data = data[!data$carDelay<0, ]
# sort data frame by totalDelay
data = data[order(-data$carDelay),]

# extract the Top 1000 DSEGs
data.top1000 = head(data,nrow(data)*0.01)
minDelay = min(data.top1000$carDelay)
maxDelay = max(data.top1000$carDelay)
data.top1000 = data.frame(dsegID = data.top1000$DsegID, delay= data.top1000$carDelay, count=data.top1000$Count, minDelay=c(1:nrow(data.top1000)), maxDelay=c(1:nrow(data.top1000)))
data.top1000$minDelay = minDelay
data.top1000$maxDelay = maxDelay

# Output Need to be activated
setwd("C:/Users/Andre/Documents/Uni/MA/R/Output/2015_DsegRanking_all")
write.table(data.top1000, file="jams2015_all_MP_WE.csv", col.names=TRUE, row.names=FALSE, sep=",")

#########################
## Evening Peak Weekend##
#########################

rm(list = ls(all = TRUE))
setwd("C:/Users/Andre/Documents/Uni/MA/R/Input")
options("scipen"=100, "digits"=4)

data = read.csv("2015_DsegRanking_all/jams2015_all_EP_WE.csv",header=TRUE, colClasses=c("character",'numeric','numeric'))
# exclude data with NaN and negative values
data = data[! data$carDelay %in% c(NaN), ]
data = data[!data$carDelay<0, ]
# sort data frame by totalDelay
data = data[order(-data$carDelay),]

# extract the Top 1000 DSEGs
data.top1000 = head(data,nrow(data)*0.01)
minDelay = min(data.top1000$carDelay)
maxDelay = max(data.top1000$carDelay)
data.top1000 = data.frame(dsegID = data.top1000$DsegID, delay= data.top1000$carDelay, count=data.top1000$Count, minDelay=c(1:nrow(data.top1000)), maxDelay=c(1:nrow(data.top1000)))
data.top1000$minDelay = minDelay
data.top1000$maxDelay = maxDelay

# Output Need to be activated
setwd("C:/Users/Andre/Documents/Uni/MA/R/Output/2015_DsegRanking_all")
write.table(data.top1000, file="jams2015_all_EP_WE.csv", col.names=TRUE, row.names=FALSE, sep=",")