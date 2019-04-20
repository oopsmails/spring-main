package com.ziyang.util;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext-rest-test.xml"})
@RunWith(MockitoJUnitRunner.class)
public class ZiyangTestJava {

    //https://stackoverflow.com/questions/4826061/what-is-the-fastest-way-to-get-the-domain-host-name-from-a-url
    public static String getHost(String url) {
        if (url == null || url.length() == 0)
            return "";

        int doubleslash = url.indexOf("//");
        if (doubleslash == -1)
            doubleslash = 0;
        else
            doubleslash += 2;

        int end = url.indexOf('/', doubleslash);
        end = end >= 0 ? end : url.length();

        int port = url.indexOf(':', doubleslash);
        end = (port > 0 && port < end) ? port : end;

        return url.substring(doubleslash, end);
    }

//	@Test
//	@Ignore
//	public void randomNumberTest() throws Exception {
//		final long TEST_SIZE = 10000000L;
//		double leastSize = TEST_SIZE * 0.95;
//		ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>(1000, 0.8f);
////        ExecutorService executor = Executors.newFixedThreadPool(5); //10000000L-317
////        ExecutorService executor = Executors.newWorkStealingPool(5); //10000000L-317
//		long startTime = System.nanoTime();
//		LongStream.range(0, TEST_SIZE)
//			.forEach(i -> {
////                    Runnable task = () -> {
////                            String randomString = RandomNumber.nextString();
////                            System.out.format("%s, randomString = %s\n", Thread.currentThread().getName(), randomString);
////                            concurrentHashMap.put(randomString, i);
////                    };
////                    executor.submit(task);
//
//				String randomString = RandomNumber.nextString(); //10000000L-243
//				System.out.format("%s, randomString = %s\n", Thread.currentThread().getName(), randomString);
//				concurrentHashMap.put(randomString, i);
//
//			});
//
////        ConcurrentUtils.stop(executor);
//		System.out.println("concurrentHashMap.size() = " + concurrentHashMap.size());
//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime) / 1000000000;
//		System.out.format("execution time = %d\n", duration);
//		assertTrue(concurrentHashMap.size() >= (int) leastSize);
//	}

    public static String getBaseDomain(String url) {
        String host = getHost(url);

        int startIndex = 0;
        int nextIndex = host.indexOf('.');
        int lastIndex = host.lastIndexOf('.');
        while (nextIndex < lastIndex) {
            startIndex = nextIndex + 1;
            nextIndex = host.indexOf('.', startIndex);
        }
        if (startIndex > 0) {
            return host.substring(startIndex);
        } else {
            return host;
        }
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    @Ignore("dev test")
    public void convertLongToDateTest() throws Exception {
//        LocalDate date = Instant.ofEpochMilli(1500903120000L).atZone(ZoneId.systemDefault()).toLocalDate();
//        System.out.println("================= date  = " + date);
        List<Long> dateList = Arrays.asList(
                1482796800000L,
                1504569600000L,
                1504656000000L,
                1504742400000L,
                1504828800000L,
                1505088000000L,
                1505174400000L,
                1505260800000L,
                1505347200000L,
                1505433600000L,
                1505692800000L,
                1505779200000L,
                1505865600000L,
                1505952000000L,
                1506038400000L
        );

        dateList.forEach(dateLong -> {
//			LocalDate localDate = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault()).toLocalDate();
//			DayOfWeek localDateDayOfWeek = localDate.getDayOfWeek();
//			System.out.println("================= localDateDayOfWeek  = " + localDate + ", it is " + localDateDayOfWeek);

            ZonedDateTime localDateTime = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault());
            DayOfWeek localDateTimeDayOfWeek = localDateTime.getDayOfWeek();
            System.out.println("================= localDateTime  = " + localDateTime + ", it is " + localDateTimeDayOfWeek);

            ZonedDateTime utcDateTime = Instant.ofEpochMilli(dateLong).atZone(ZoneOffset.UTC);
            DayOfWeek utcDateTimeDayOfWeek = utcDateTime.getDayOfWeek();
            System.out.println("================= utcDateTime  = " + utcDateTime + ", it is " + utcDateTimeDayOfWeek);

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        });

        assertTrue(dateList != null);
    }

    @Test
    public void convertLongToDateTest2() throws Exception {
//        LocalDate date = Instant.ofEpochMilli(1500903120000L).atZone(ZoneId.systemDefault()).toLocalDate();
//        System.out.println("================= date  = " + date);
        List<Long> dateList = Arrays.asList(
                1504013459000L,
                1504013520000L
        );

        dateList.forEach(dateLong -> {
            LocalDate localDate = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault()).toLocalDate();
            DayOfWeek localDateDayOfWeek = localDate.getDayOfWeek();
            System.out.println("================= localDateDayOfWeek  = " + localDate + ", it is " + localDateDayOfWeek);

            ZonedDateTime localDateTime = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault());
            DayOfWeek localDateTimeDayOfWeek = localDateTime.getDayOfWeek();
            System.out.println("================= localDateTime  = " + localDateTime + ", it is " + localDateTime);

            System.out.println("================= localDateDayOfWeek long = " + localDateTime.toInstant().toEpochMilli());

            ZonedDateTime utcDateTime = Instant.ofEpochMilli(dateLong).atZone(ZoneOffset.UTC);
            DayOfWeek utcDateTimeDayOfWeek = localDateTime.getDayOfWeek();
            System.out.println("================= utcDateTime  = " + utcDateTime + ", it is " + utcDateTimeDayOfWeek);

            System.out.println("================= utcDateTime long = " + utcDateTime.toInstant().toEpochMilli());

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        });

        assertTrue(dateList != null);
    }

    @Test(expected = NullPointerException.class)
    public void testStreamNullOrEmpty() throws Exception {
        List<String> nullList = null;
        nullList.forEach(System.out::println);
        assertTrue(nullList == null);
    }

    @Test
    public void testStringLength() throws Exception {
        String str1 = "abc";
        assertEquals("abc", str1.substring(0, 3));

        String str2 = "abcd";
        assertEquals("abc", str2.substring(0, 3));

//		assertEquals("UNK", ClientAccountDefunctSecuritiesDaoJdbcImpl.reviseMarket(MarketType.NONE.name()));
//		assertEquals("OTH", ClientAccountDefunctSecuritiesDaoJdbcImpl.reviseMarket(MarketType.OTHER.name()));
//		assertEquals("CA", ClientAccountDefunctSecuritiesDaoJdbcImpl.reviseMarket(MarketType.CA.name()));
//		assertEquals("US", ClientAccountDefunctSecuritiesDaoJdbcImpl.reviseMarket(MarketType.US.name()));
    }

    @Test
    public void testOptional() throws Exception {
        Optional<String> gender = Optional.of("MALE");

        String answer1 = "Yes";
        String answer2 = null;

        System.out.println("Non-Empty Optional:" + gender);
        System.out.println("Non-Empty Optional: Gender value : " + gender.get());
        System.out.println("Empty Optional: " + Optional.empty());

        System.out.println("ofNullable on Non-Empty Optional: " + Optional.ofNullable(answer1));
        System.out.println("ofNullable on Empty Optional: " + Optional.ofNullable(answer2));

        // java.lang.NullPointerException
//		System.out.println("ofNullable on Non-Empty Optional: " + Optional.of(answer2));

        System.out.println("returning Optional: " + Optional.ofNullable(answer1));

        getOptionalDate().ifPresent(System.out::println);
        getOptionalString().ifPresent(System.out::println);

        assertEquals("MALE", gender.get());
    }

    @Test
    public void testOptionalListOfList() throws Exception {
        List<List<String>> outerList = new ArrayList<>();
        List<String> innerList = null;

        outerList.add(innerList);

        String test = Optional.ofNullable(outerList)
                .filter(c -> !c.isEmpty())
                .map(listOfList -> listOfList.get(0))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .orElse(null);

        assertTrue(test == null);
    }

//	private String getHostName1(String url) {
//		URI uri = null;
//		try {
//			uri = new URI(url);
//		} catch (URISyntaxException e) { //MalformedURLException | URISyntaxException
//			e.printStackTrace();
//		}
//		String hostname = uri.getHost();
//		if (hostname != null) {
//			return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
//		}
//		return hostname;
//	}
//
//	private String getHostName2(String url) {
//		Pattern p = Pattern.compile(".*?([^.]+\\.[^.]+)");
//		URI uri = null;
//		try {
//			uri = new URI(url);
//		} catch (URISyntaxException e) { //MalformedURLException | URISyntaxException
//			e.printStackTrace();
//		}
//		Matcher m = p.matcher(uri.getHost());
//		if (m.matches()) {
//			return m.group(1);
//		}
//		return null;
//	}

    @Test
    public void testIntArray() throws Exception {
        int[] ints = new int[]{0, 1, 1, 0, 1};
        int ones = (int) Arrays.stream(ints).filter(i -> i == 1).count();

        assertTrue(ones == 3);
    }

    @Test
    public void testGetTopDomainFromUrl() throws Exception {
        ArrayList<String> cases = new ArrayList<>();
        cases.add("www.google.com");
        cases.add("ww.socialrating.it");
        cases.add("www-01.hopperspot.com");
        cases.add("wwwsupernatural-brasil.blogspot.com");
        cases.add("xtop10.net");
        cases.add("zoyanailpolish.blogspot.com");
        //--------------------
        cases.add("https://www.google.com");
        cases.add("https://www.google.com/");
        cases.add("https://localhost.com:8080/");
        //--------------------
        cases.add("amazon.co.uk");
        cases.add("www.foo.co.uk/bar");

        cases.forEach((String url) -> {
//			InternetDomainName topDomain = InternetDomainName.from(url).topPrivateDomain();
//			System.out.println("InternetDomainName, topDomain --> " + topDomain);

            String hostName = getBaseDomain(url);
            System.out.println("hostName2 --> " + hostName);
        });
        assertNotNull(cases);
    }

    private Optional<Date> getOptionalDate() {
        Date date = new Date();
        return Optional.ofNullable(date);
    }

    private Optional<String> getOptionalString() {
        Date date = null;
        return Optional.ofNullable(date)
                .map(Date::toString)
                .map(s -> s.substring(0, 1));
    }


    public static class ConcurrentUtils {

        public static void stop(ExecutorService executor) {
            try {
                executor.shutdown();
                executor.awaitTermination(500, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.err.println("termination interrupted");
            } finally {
                if (!executor.isTerminated()) {
                    System.err.println("killing non-finished tasks");
                }
                executor.shutdownNow();
            }
        }

        public static void sleep(int seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

    }
}
