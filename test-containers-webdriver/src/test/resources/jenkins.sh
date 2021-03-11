#!/bin/bash

AWS_KEY=$1
AWS_ACCESS_KEY=$2
AWS_SECRET_KEY=$3
AWS_SECURITY_GROUP=$4

chmod 400 $AWS_KEY
aws configure set aws_access_key_id $AWS_ACCESS_KEY && \
     aws configure set aws_secret_access_key $AWS_SECRET_KEY && \
     aws configure set default.region us-east-1

#Create EC2 instance
aws ec2 run-instances --image-id ami-09d95fab7fff3776c \
      --instance-type t2.large \
      --security-group-ids $AWS_SECURITY_GROUP \
      --key-name $AWS_KEY \
      --tag-specifications 'ResourceType=instance,Tags=[{Key=SERVER,Value=automatedTests}]'

EC2_IP=$(aws ec2 describe-instances --filters 'Name=tag:SERVER,Values=automatedTests' | grep PublicIpAddress | awk '{print $2}' | cut -d '"' -f 2)
EC2_ID=$(aws ec2 describe-instances --filters 'Name=tag:SERVER,Values=automatedTests' | grep InstanceId | awk '{print $2}' | cut -d '"' -f 2)

#Install prerequisites
ssh -o StrictHostKeyChecking=no -i $AWS_KEY ec2-user@$EC2_IP sudo yum update -y
ssh -i $AWS_KEY ec2-user@$EC2_IP sudo yum install docker -y
ssh -i $AWS_KEY ec2-user@$EC2_IP sudo service docker start
ssh -i $AWS_KEY ec2-user@$EC2_IP sudo usermod -a -G docker ec2-user
ssh -i $AWS_KEY ec2-user@$EC2_IP sudo curl -L "https://github.com/docker/compose/releases/download/1.23.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
ssh -i $AWS_KEY ec2-user@$EC2_IP sudo chmod +x /usr/local/bin/docker-compose
scp -i $AWS_KEY src/test/resources/Dockerfile ec2-user@$EC2_IP:/home/ec2-user/
scp -i $AWS_KEY src/test/resources/docker-compose.yml ec2-user@$EC2_IP:/home/ec2-user/

#Build everything and run tests
ssh -i $AWS_KEY ec2-user@$EC2_IP docker-compose up -d
ssh -i $AWS_KEY ec2-user@$EC2_IP docker build -t clone-image .
ssh -i $AWS_KEY ec2-user@$EC2_IP docker run -d --net ec2-user_grid --name automated-code -it clone-image
ssh -i $AWS_KEY ec2-user@$EC2_IP docker exec -w /cloud-selenium-test/ automated-code mvn clean test
ssh -i $AWS_KEY ec2-user@$EC2_IP docker cp automated-code:cloud-selenium-test/target/surefire-reports/testng-results.xml .
scp -i $AWS_KEY ec2-user@$EC2_IP:/home/ec2-user/testng-results.xml .

#Delete EC2 instance
aws ec2 terminate-instances --instance-ids $EC2_ID
