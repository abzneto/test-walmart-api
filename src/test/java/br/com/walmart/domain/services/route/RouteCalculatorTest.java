package br.com.walmart.domain.services.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.walmart.application.representation.RouteResultInformation;
import br.com.walmart.domain.model.route.Route;

public class RouteCalculatorTest {
	
	private RouteCalculator calculator;
	
	private List<Route> routes;
	
	@BeforeClass
	public static void setUpClass() {
		FixtureFactoryLoader.loadTemplates("br.com.walmart.infrastructure.fixture");
	}
	
	@Before
	public void setUp() {
		calculator = new RouteCalculator();
		routes = new ArrayList<Route>();
		
		Route route1 = Fixture.from(Route.class).gimme("route1");
		Route route2 = Fixture.from(Route.class).gimme("route2");
		Route route3 = Fixture.from(Route.class).gimme("route3");
		Route route4 = Fixture.from(Route.class).gimme("route4");
		Route route5 = Fixture.from(Route.class).gimme("route5");
		Route route6 = Fixture.from(Route.class).gimme("route6");
		
		routes.add( route1 );
		routes.add( route2 );
		routes.add( route3 );
		routes.add( route4 );
		routes.add( route5 );
		routes.add( route6 );
	}
	
	@Test
	public void shouldBeCalculateCostRoute() {
		RouteResultInformation result = calculator.calculate(routes, "A", "D", 10d, 2.5);

		assertTrue( result.getCost() == 6.25 );
	}
	
	@Test
	public void shouldBeCalculatePathRoute() {
		RouteResultInformation result = calculator.calculate(routes, "A", "D", 10d, 2.5);

		assertTrue( ("A -> B -> D").equals(result.getPath()) );
	}
	
	@Test
	public void shouldNotCalculateRouteByNullRoutes() {		
		assertEquals(calculator.calculate(null, "A", "D", 10d, 2.5), null);
	}
	
	@Test
	public void shouldNotCalculateRouteByNullDestination() {		
		assertEquals(calculator.calculate(routes, "A", null, 10d, 2.5), null);
	}
	
	@Test
	public void shouldNotCalculateRouteByNullOrigin() {		
		assertEquals(calculator.calculate(routes, null, "D", 10d, 2.5), null);
	}
	
	@Test
	public void shouldNotCalculateCostByZeroAutonomy() {		
		assertEquals(calculator.calculate(routes, "A", "D", 0d, 2.5), null);
	}
	
	@Test
	public void shouldNotCalculateCostByZeroValueLiter() {		
		assertEquals(calculator.calculate(routes, "A", "D", 10d, 0d), null);
	}
}
