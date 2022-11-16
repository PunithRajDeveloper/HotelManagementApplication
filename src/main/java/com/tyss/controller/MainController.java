package com.tyss.controller;



import java.util.Scanner;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import java.util.ArrayList;
import java.util.List;



import com.tyss.dao.FoodOrderDao;
import com.tyss.dao.MenuDao;
import com.tyss.dao.UserDao;
import com.tyss.dto.FoodItems;
import com.tyss.dto.FoodOrder;
import com.tyss.dto.Menu;
import com.tyss.dto.Product;
import com.tyss.dto.User;



public class MainController {
	public static void main(String[] args) {
		UserDao dao = new UserDao();
		boolean b = true;
		Scanner sc = new Scanner(System.in);
		while (b) {
			System.out.println("wellcome to HalliManeHotel Please go through the below options");
			System.out.println("---press  1  for Login-----");
			System.out.println("---press  2  for Signup----");
			System.out.println("---press  3  for logout-----");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				System.out.println("Enter your email");
				String email = sc.next();
				System.out.println("Enter ypur password");
				String password = sc.next();
				User user = dao.findUserByEmail(email);
				if (user.getPassword().equals(password)) {
					if (user.getRole().equals("Manager")) {
						System.out.println("1.Create Menu");
						System.out.println("2.Update Menu");
						int menuchoice = sc.nextInt();
						switch (menuchoice) {
						case 1:
							Menu menu = new Menu();
							System.out.println("Enter the Menu name");
							menu.setName(sc.next());
							boolean menub = true;
							List<FoodItems> list = new ArrayList<FoodItems>();
							while (menub) {
								FoodItems items = new FoodItems();
								System.out.println("Enter the item name");
								items.setName(sc.next());
								System.out.println("Enter thr price ");
								items.setPrice(sc.nextDouble());
								System.out.println("Enter the desc");
								items.setDesc(sc.next());
								list.add(items);
								System.out.println("Enter 1 to add another item");
								if (sc.nextInt() == 1) {
									menub = true;
								} else {
									menub = false;
								}
							}
							menu.setItems(list);
							MenuDao menuDao = new MenuDao();
							menuDao.saveMenu(menu);

							break;
						case 2:
							// update menu
							break;

						default:
							break;
						}

					} else if (user.getRole().equals("Staff")) {
						System.out.println("1.Create FoodOrder");
						System.out.println("2.Update FoodOrder");
						int orderchoice = sc.nextInt();
						switch (orderchoice) {
						case 1:
							FoodOrder foodOrder = new FoodOrder();
							System.out.println("Enter the customer name");
							foodOrder.setName(sc.next());
							System.out.println("Enter the customer address");
							foodOrder.setAddress(sc.next());
							System.out.println("Enter the customer phone");
							foodOrder.setPhone(sc.nextLong());
							System.out.println("Pick the Menu");
							int menuid = sc.nextInt();
							MenuDao menuDao = new MenuDao();
							Menu menu = menuDao.getMenuById(menuid);
							List<FoodItems> foodItems = menu.getItems();
							for (FoodItems foodItem : foodItems) {
								System.out.println("Food Id " + foodItem.getId());
								System.out.println("Food Name " + foodItem.getName());
								System.out.println("Food Desc " + foodItem.getDesc());
								System.out.println("Food Price " + foodItem.getPrice());

							}
							FoodItems item=new FoodItems();
							System.out.println("Enter the food Id ");
							int foodId = sc.nextInt();
							System.out.println("Enter the quantity");
							int quantity = sc.nextInt();
							List<Product> products = new ArrayList<Product>();
							Product product = new Product();

							product.setQuantity(quantity);
							for (FoodItems items : foodItems) {
								if (items.getId() == foodId) {
									product.setName(items.getName());
									product.setPrice(items.getPrice());
									products.add(product);
								}

							}
							double totalcost = 0;
							for (Product p : products) {
								totalcost = (totalcost) + (p.getQuantity() * p.getPrice());
								foodOrder.setTotalcost(quantity);
							}
							foodOrder.setTotalcost(totalcost);
							foodOrder.setList(products);
							FoodOrderDao dao2 = new FoodOrderDao();
							dao2.saveMenu(foodOrder);
							
							double total_gst = (totalcost+((totalcost*(5/100))));
                            System.out.println("===================================================================================");
							System.out.println("||-------------------------bill-recipt--------------------------------------------");
							System.out.println("||1. Name of the customer                                         :- "+foodOrder.getName());
							System.out.println("||2. Address of the customer                                      :- "+foodOrder.getAddress());
							System.out.println("||4. Coustomer phone number                                       :- "+foodOrder.getPhone());
							System.out.println("||5. Food order id                                                :- "+foodId);
							System.out.println("||5. Food order  quantity                                         :- "+quantity);
							System.out.println("||6. Food order total                                             :- "+totalcost);
							System.out.println("||7. Food order tatal amount needs to be pay with 5% gst is       :- "+total_gst);
							System.out.println("||----------------------- thanks -- visit -- again -------------------------------");
							System.out.println("===================================================================================");
							break;

						default:
							break;
						}

					}
				} else {
					System.out.println("Invalid Password");
					break;
				}
			}
			break;
			case 2:
				System.out.println("Enter your name");
				String name = sc.next();
				System.out.println("Enter your email");
				String email = sc.next();
				System.out.println("Enter your password");
				String password = sc.next();
				System.out.println("Enter your phone");
				long phone = sc.nextLong();
				System.out.println("Enter your choice for role \n 1.Manager \n 2.Staff");
				int rolechoice = sc.nextInt();
				User user = new User();
				switch (rolechoice) {
				case 1:
					user.setRole("Manager");
					break;
				case 2:
					user.setRole("Staff");
					break;

				}
				System.out.println("Enter your company");
				String company = sc.next();

				user.setName(name);
				user.setEmail(email);
				user.setPassword(password);
				user.setPhone(phone);
				user.setCompany(company);;
				dao.saveUser(user);
				System.out.println("Sucessfully signed up");

				break;
			case 3:
			{
				System.exit(0);
				System.out.println("---------thank you for using our application----------");
			}
			default:
				b = false;
				break;
			}
		}
	}

}