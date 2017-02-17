rm(list = ls(all = TRUE))
setwd("C:/Users/Andre/Documents/Uni/MA/R/Input")

# Loading libraries
library(timeSeries)
library(timeDate)
library(parsedate)
library(lubridate)

# Reading the timestamp and count column from given data
data = read.csv("timeseries_q3_2015.csv",header=TRUE,colClasses=c(NA,"NULL","NULL","NULL","NULL",NA))
data$timestamp = ymd_hms(data$timestamp)
data = data.frame(timestamp = data$timestamp, count = data$count, month = month(data$timestamp) ,week = week(data$timestamp) ,weekday = weekdays(data$timestamp), hour = hour(data$timestamp), minute = minute(data$timestamp))

###Examples for calenderweek 39
# getting dataframe
# full week (39th) - complete
data.week39 = data[grep(39, data$week), ] 
# Thu 39th week 
data.thu.week39 = data.week39[grep("Donnerstag", data.week39$weekday), ]

# make / save plots
pdf('fullWeek.pdf')
plot(data.week39$timestamp, data.week39$count, xlab="Date", ylab="Number of announced jams", ylim=c(0,500), main="Week 39 in 2015", type="l", lwd="2")
dev.off()
pdf('CW39_Thu.pdf')
plot(data.thu.week39$timestamp, data.thu.week39$count, xlab="time(hh:mm)", ylab="Number of announced jams", ylim=c(0,500), main="Thu 24th Sept 2015 (CW 39)", type="l", lwd="2")
dev.off()

######	######
##  other   ##
######	######

# Mondays
data.monday = data[grep("Montag", data$weekday), ]
# Monday 27th week
data.mon.week27 = data.monday[grep(27, data.monday$week), ]
head(data.monday)

#computing the mean of the counts for the levels of hour (24)
firstTS = data.monday[1,1]
lastEntryNr = nrow(data.monday)
lastTS =data.monday[lastEntryNr,1]
by = as.list(timeSequence(from = firstTS, to = lastTS, by = dseconds(30)))
x= aggregate(data, by, FUN=mean)[1]

x = aggregate(data.monday$count, by=list(data.monday$minute), FUN=mean)[2]

# trying to set intervals
firstTime = df[1,1] # 2015-07-01 00:00:30
secondTime = df[2,1] # 2015-07-01 00:01:00
interval = interval(firstTime, secondTime)
# interval = 0.5 minutes

secondTime = firstTime+dminutes(5)
secondTime # 2015-07-01 00:05:30
interval = interval(firstTime, secondTime)
interval / dminutes(1)
interval # 5 minutes

#write csv
write.table(df.week39, file="jams_week39.csv", col.names=TRUE, row.names=FALSE, sep=",")
