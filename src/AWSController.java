import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.Statistic;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

public final class AWSController {
		private final AmazonEC2 ec2;
		
		private AWSController()
		{
			ec2 = new AmazonEC2Client(new ProfileCredentialsProvider());			
			//Need to be used on ec2 Instances
			//ec2 = new AmazonEC2Client(new InstanceProfileCredentialsProvider());
			Region euCentral = Region.getRegion(Regions.EU_CENTRAL_1);
			ec2.setRegion(euCentral);
		}       
		
		public static AWSController getNewAWSControllerInstance()
		{ 
			return new AWSController();
		}
		
		public void UpdateAlarms()
		{
			for (Instance instance : this.getAllActiveInstances()) {
				this.CreateDefaultAlarm(instance.getInstanceId());
			}
		}
		
		private void CreateDefaultAlarm(String instanceId)
		{	
			AmazonCloudWatchClient cloudWatch = new AmazonCloudWatchClient();
			cloudWatch.setEndpoint("monitoring.eu-central-1.amazonaws.com");
			
			Dimension dimension = new Dimension();
			dimension.setName("InstanceId");
			dimension.setValue(instanceId);
			
			cloudWatch.putMetricAlarm(new PutMetricAlarmRequest()
				.withAlarmName(instanceId)
				.withStatistic(Statistic.Average)
				.withThreshold(20.00)
				.withPeriod(60)
				.withMetricName("CPUUtilization")
				.withNamespace("AWS/EC2")
				.withComparisonOperator(ComparisonOperator.LessThanOrEqualToThreshold)
				.withDimensions(dimension)
				.withAlarmActions("arn:aws:automate:eu-central-1:ec2:terminate")
				.withEvaluationPeriods(1)
				.withActionsEnabled(true));		
		}
		
		void runNewCheckstyleInstances(int amountInstances)
		{
			// Initializes a Spot Instance Request
			RunInstancesRequest runInstancesRequest = new RunInstancesRequest() //
	        	.withInstanceType(InstanceType.T2Micro.toString()) //
	        	.withImageId("ami-d3c022bc") //
	        	.withMinCount(amountInstances) //
	        	.withMaxCount(amountInstances)
	        	.withMonitoring(true);
			        //.withSecurityGroupIds("accept-all") //
		            //.withKeyName("cleclerc");
			ec2.runInstances(runInstancesRequest);
		}
		
		List<Instance> getAllInstances()
		{
			DescribeInstancesResult result = ec2.describeInstances();
	        List<Reservation> reservations = result.getReservations();
	        List<Instance> instances = new ArrayList();
	        
	        for (Reservation reservation : reservations) {
	        	instances.addAll(reservation.getInstances());
	        }
			return instances;			
		}
		
		List<Instance> getAllActiveInstances()
		{
			List<Instance> instances = new ArrayList();
			for (Instance instance : this.getAllInstances()) {
				if(instance.getState().getCode()==16 || instance.getState().getCode()==0)
				{
					instances.add(instance);
				}
			}
			return instances;			
		}
		
		void TerminateOldestInstances()
		{
			Date oldest = new Date();
			for (Instance instance : this.getAllInstances()) {
				if(oldest.after(instance.getLaunchTime()))
				{
					oldest = instance.getLaunchTime();
				}
			}
			System.out.println(oldest);
		}
		
//		void TerminateAllOver1Hour()
//		{
//			Date curDate = new Date();
//			for (Instance instance : this.getAllActiveInstances()) {
//				if(curDate - instance.getLaunchTime() > 10)
//			}
//		}
		
		void TerminateAllInstance()
		{
			for (Instance instance : this.getAllInstances()) {
				ec2.terminateInstances(new TerminateInstancesRequest().withInstanceIds(instance.getInstanceId()));
			}
		}
}
