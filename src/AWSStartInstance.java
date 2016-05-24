import com.amazonaws.services.ec2.model.Instance;


public class AWSStartInstance {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AWSController controller = AWSController.getNewAWSControllerInstance();
        
		controller.runNewCheckstyleInstances(10);
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
