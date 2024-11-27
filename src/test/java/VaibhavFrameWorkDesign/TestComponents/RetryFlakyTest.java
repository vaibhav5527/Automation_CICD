package VaibhavFrameWorkDesign.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

//By implementing IRetryAnalyzer interface, we can rerun the failure tests one more time to make sure that those failures are real.

public class RetryFlakyTest implements IRetryAnalyzer{
	
	int count = 0;
	int maxTry = 1;
	//after completing those listeners, if it tc failed, it'll come here to check do I need to rerun again
	@Override
	public boolean retry(ITestResult result) {
		// you already tried maximum times, if conditional fails then that tc marked as failed in report
		if(count<maxTry)
		{
			count++;
			return true; //As long as this method returns true, your test will keep on retrying again and again
		}
		return false; //it'll stop retrying. once if condition failed
	}

}
