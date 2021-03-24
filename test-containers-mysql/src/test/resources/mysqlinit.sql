CREATE TABLE `Product` (
  `ProductId` int unsigned NOT NULL AUTO_INCREMENT,
  `ProductName` char(30) DEFAULT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  UNIQUE KEY `ProductId` (`ProductId`)
);