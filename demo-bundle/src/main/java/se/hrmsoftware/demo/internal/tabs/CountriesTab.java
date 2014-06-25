package se.hrmsoftware.demo.internal.tabs;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import org.osgi.service.component.annotations.Reference;
import se.hrmsoftware.bundles.db.migration.liquibase.MigratedDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Shows a Vaadin table component containing the entries in the COUNTRIES table.
 */
@org.osgi.service.component.annotations.Component
public class CountriesTab implements ComponentFactory {

	private DataSource dataSource;

	@Reference(target = "(osgi.jdbc.datasource.id=h2)")
	protected void setDataSource(MigratedDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String getLabel() {
		return "Countries";
	}

	@Override
	public Component create() {
		try {
			SQLContainer sqlContainer = new SQLContainer(new TableQuery("COUNTRIES", new J2EEConnectionPool(dataSource)));
			Table table = new Table("COUNTRIES Table", sqlContainer);
			table.setWidth(100f, Sizeable.Unit.PERCENTAGE);
			return table;
		}
		catch (SQLException e) {
			throw new RuntimeException("Wtf", e);
		}
	}
}
