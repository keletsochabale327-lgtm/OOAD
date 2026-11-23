
package banking.controller;

import banking.view.DashboardView;

public class DashboardController {

    private DashboardView dashboardView;

    public DashboardController(DashboardView dashboardView) {
        this.dashboardView = dashboardView;

        dashboardView.getLogoutButton().setOnAction(event -> logout());
    }

    private void logout() {
        System.out.println("User has logged out.");
    }
}

