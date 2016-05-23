import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.applicationdiscovery.model.CreateTagsRequest;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.LaunchSpecification;
import com.amazonaws.services.ec2.model.RequestSpotInstancesRequest;
import com.amazonaws.services.ec2.model.RequestSpotInstancesResult;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.SpotInstanceRequest;

public class CreateInstaces {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AWSController controller = AWSController.getNewAWSControllerInstance();
        
		//controller.runNewCheckstyleInstances(2);
		//controller.TerminateAllInstance();
        controller.UpdateAlarms();
		for (Instance instance : controller.getAllActiveInstances()) {
        		System.out.println(instance.getInstanceType());
                System.out.println(instance.getInstanceId());
                System.out.println(instance.getMonitoring());
                System.out.println();
        	
        }
        //RequestSpotInstancesResult requestResult = ec2.requestSpotInstances(runInstancesRequest);
        //List<SpotInstanceRequest> requestResponses = requestResult.getSpotInstanceRequests();

        // Setup an arraylist to collect all of the request ids we want to watch hit the running
        // state.
      //  spotInstanceRequestIds = new ArrayList<String>();

        // Add all of the request ids to the hashset, so we can determine when they hit the
        // active state.
        //for (SpotInstanceRequest requestResponse : requestResponses) {
        //    System.out.println("Created Spot Request: "+requestResponse.getSpotInstanceRequestId());
        //    spotInstanceRequestIds.add(requestResponse.getSpotInstanceRequestId());
        //}
	}
	
	
}
