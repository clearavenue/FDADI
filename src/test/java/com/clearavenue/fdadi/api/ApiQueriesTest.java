package com.clearavenue.fdadi.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiQueriesTest {

	@Test
	public void getLabelTest() {
		String expected = "{\"set_id\":\"0025eb89-590c-4451-8a06-b129686cf75a\",\"indications_and_usage\":[\"Uses temporarily relieves minor aches and pains other therapy as recommended by your doctor. Because of its delayed action, this product will not provide fast relief of headaches, fever, or other symptoms needing immediate relief.\"],\"other_safety_information\":[\"Other information store at controlled room temperature 15�-30�C (59�-86�F) do not use if imprinted safety seal under cap is broken or missing\"],\"stop_use\":[\"Stop use and ask a doctor if you experience any of the following signs of stomach bleeding: feel faint vomit blood have bloody or black stools have stomach pain that does not get better allergic reaction occurs ringing in the ears or a loss of hearing occurs pain gets worse or lasts more than 10 days fever gets worse or lasts more than 3 days any new symptoms appear redness or swelling is present in the painful area\"],\"keep_out_of_reach_of_children\":[\"Keep out of reach of children In case of overdose, get medical help or contact a Poison Control Center right away.\"],\"ask_doctor\":[\"Ask a doctor before use if stomach bleeding warning applies to you you have a history of stomach problems, such as heartburn you have high blood pressure, heart disease, liver cirrhosis, or kidney disease you are taking a diuretic you have asthma\"],\"questions\":[\"Questions or comments? Call toll free 1-877-753-3935\"],\"dosage_and_administration\":[\"Directions do not exceed recommended dosage drink a full glass of water with each dose adults and children 12 years of age and over: take 4 to 8 tablets every 4 hours not to exceed 48 tablets in 24 hours, unless directed by a doctor children under 12 years of age: consult a doctor\"],\"purpose\":[\"Purpose Pain reliever\"],\"ask_doctor_or_pharmacist\":[\"Ask a doctor or pharmacist before use if you are taking a prescription drug for: anticoagulation (thinning of the blood) gout diabetes arthritis\"],\"do_not_use\":[\"Do not use if you have ever had an allergic reaction to any other pain reliever/fever reducer\"],\"version\":\"2\",\"id\":\"8244057c-8522-4871-8576-a15b9583cbb7\",\"pregnancy_or_breast_feeding\":[\"If pregnant or breast- feeding, ask a health professional before use. It is especially important not to use aspirin during the last 3 months of pregnancy unless definitely directed to do so by a doctor because it may cause problems in the unborn child or complications during delivery.\"],\"package_label_principal_display_panel\":[\"Principal Display Panel Compare to the active ingredient in ASPIRIN REGIMEN BAYER� 81 mg** SEE NEW WARNINGS INFORMATION Low Dose 81 mg ASPIRIN adult low strength Pain reliever (NSAID)* KEEP OUTER CARTON FOR COMPLETE WARNINGS AND PRODUCT INFORMATION **This product is not manufactured or distributedby Bayer Corporation Consumer care division, owner of the registered trademark Aspirin Regimen Bayer� 81 mg\",\"Product Label Enteric coated Aspirin 81 mg Valu merchandisers Best Choice\"],\"inactive_ingredient\":[\"Inactive ingredients *acetylated monoglycerides, *anhydrous lactose, *carnauba wax, colloidal silicon dioxide,*corn starch, *croscarmellose sodium, D&C Yellow #10 Aluminum Lake, FD&C Yellow #6 Aluminum Lake, hypromellose, *hypromellose phthalate, *iron oxide Yellow (iron oxide ochre), methacrylic acid copolymer, microcrystalline cellulose, *mineral oil, *polyethylene glycol (PEG)-400, *polysorbate 80, povidone, pregelatinized starch, *propylene glycol, *simethicone, silicon dioxide, sodium bicarbonate, sodium hydroxide, sodium lauryl sulfate, starch, stearic acid, talc, titanium dioxide, triacetin, and triethyl citrate. *May also contain.\"],\"active_ingredient\":[\"Active ingredient (in each tablet) Aspirin 81 mg (NSAID)* *nonsteroidal anti- inflammatory drug\"],\"@epoch\":1.416451272131902E9,\"effective_time\":\"20121114\",\"openfda\":{\"unii\":[\"R16CO5Y76E\"],\"spl_id\":[\"8244057c-8522-4871-8576-a15b9583cbb7\"],\"product_ndc\":[\"63941-440\"],\"substance_name\":[\"ASPIRIN\"],\"rxcui\":[\"308416\"],\"spl_set_id\":[\"0025eb89-590c-4451-8a06-b129686cf75a\"],\"product_type\":[\"HUMAN OTC DRUG\"],\"pharm_class_cs\":[\"Nonsteroidal Anti-inflammatory Compounds [Chemical/Ingredient]\"],\"manufacturer_name\":[\"Valu Merchandisers Company (Best Choice)\"],\"brand_name\":[\"Aspirin Adult low strength\"],\"pharm_class_pe\":[\"Decreased Prostaglandin Production [PE]\"],\"is_original_packager\":[true],\"nui\":[\"N0000000160\",\"N0000175721\",\"N0000008836\",\"N0000175722\"],\"route\":[\"ORAL\"],\"pharm_class_moa\":[\"Cyclooxygenase Inhibitors [MoA]\"],\"package_ndc\":[\"63941-440-12\"],\"pharm_class_epc\":[\"Nonsteroidal Anti-inflammatory Drug [EPC]\"],\"generic_name\":[\"ASPIRIN\"],\"application_number\":[\"part343\"]},\"spl_product_data_elements\":[\"AspirinAdult low strength Aspirin ASPIRIN ASPIRIN DIACETYLATED MONOGLYCERIDES ANHYDROUS LACTOSE CARNAUBA WAX SILICON DIOXIDE STARCH, CORN CROSCARMELLOSE SODIUM D&C YELLOW NO. 10 HYPROMELLOSES HYPROMELLOSE PHTHALATE (24% PHTHALATE, 55 CST) FERRIC OXIDE YELLOW METHACRYLIC ACID - METHYL METHACRYLATE COPOLYMER (1:1) CELLULOSE, MICROCRYSTALLINE MINERAL OIL POLYETHYLENE GLYCOL 400 POLYSORBATE 80 POVIDONES STARCH, CORN PROPYLENE GLYCOL DIMETHICONE SILICON DIOXIDE SODIUM BICARBONATE SODIUM HYDROXIDE SODIUM LAURYL SULFATE STARCH, CORN STEARIC ACID TALC TITANIUM DIOXIDE TRIACETIN TRIETHYL CITRATE FD&C YELLOW NO. 6 ALUMINUM OXIDE E;HEART;81\"],\"warnings\":[\"Warnings Reye\\u2019s syndrome: Children and teenagers who have or are recovering from chicken pox or flu-like symptoms should not use this product. When using this product, if changes in behavior with nausea and vomiting occur, consult a doctor because these symptoms could be an early sign of Reye\\u2019s syndrome, a rare but serious illness. Allergy alert: Aspirin may cause a severe allergic reaction which may include: hives facial swelling asthma(wheezing) shock Stomach bleeding warning: This product contains a nonsteroidal anti-inflammatory drug (NSAID), which may cause stomach bleeding. The chance is higher if you: are age 60 or older have had stomach ulcers or bleeding problems take a blood thinning (anticoagulant) or steroid drug take other drugs containing prescription or nonprescription NSAIDs (aspirin, ibuprofen, naproxen, or others) have 3 or more alcoholic drinks every day while using this product take more or for a longer time than directed Do not use if you have ever had an allergic reaction to any other pain reliever/fever reducer Ask a doctor before use if stomach bleeding warning applies to you you have a history of stomach problems, such as heartburn you have high blood pressure, heart disease, liver cirrhosis, or kidney disease you are taking a diuretic you have asthma Ask a doctor or pharmacist before use if you are taking a prescription drug for: anticoagulation (thinning of the blood) gout diabetes arthritis Stop use and ask a doctor if you experience any of the following signs of stomach bleeding: feel faint vomit blood have bloody or black stools have stomach pain that does not get better allergic reaction occurs ringing in the ears or a loss of hearing occurs pain gets worse or lasts more than 10 days fever gets worse or lasts more than 3 days any new symptoms appear redness or swelling is present in the painful area If pregnant or breast- feeding, ask a health professional before use. It is especially important not to use aspirin during the last 3 months of pregnancy unless definitely directed to do so by a doctor because it may cause problems in the unborn child or complications during delivery. Keep out of reach of children In case of overdose, get medical help or contact a Poison Control Center right away.\"]}";

		try {
			String actual = ApiQueries.getLabel("aspirin");
			assertEquals(expected, actual);
		} catch (UnirestException e) {
			fail("Should not have thrown UnirestException:" + e.getMessage());
		}
	}

	@Test
	public void getLabelNotExistTest() {
		try {
			String expected = "";
			String actual = ApiQueries.getLabel("drugThatDoesntExist");
			assertEquals(expected, actual);
		} catch (UnirestException e) {
			fail("Should not have thrown UnirestException:" + e.getMessage());
		}
	}

	@Test
	public void getRecallStatusTest(){
		String expectedReason = "Lack of Assurance of Sterility";
		String expectedDistribution = "Nationwide";
		String expectedDescription = "Lidocaine/Dextrose 5%/7.5% PF, manufactured by New England Compounding Center, Framingham, MA";
		int expectedLength = 10;
		
		try {
			RecallEvent[] actualRecalls = ApiQueries.getRecallStatus("Dextrose", 10);
			assertEquals(expectedReason, actualRecalls[1].getReason());
			assertEquals(expectedDistribution, actualRecalls[1].getDistributionPattern());
			assertEquals(expectedDescription, actualRecalls[1].getProductDescription());
			assertEquals(expectedLength, actualRecalls.length);
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("Unirest exception when testing ApiQueries.getRecallStatus()");
		}
	}
	
	@Test
	public void getRecallStatusNotExistTest(){
		try {
			RecallEvent[] results = ApiQueries.getRecallStatus("drugThatDoesntExist", 4);
			assertNull(results);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
