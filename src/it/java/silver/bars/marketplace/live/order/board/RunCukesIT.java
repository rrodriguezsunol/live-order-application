package silver.bars.marketplace.live.order.board;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "target/test-classes")
public class RunCukesIT {

}