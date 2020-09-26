package com.gailo22.mysuperapp.home

import android.os.Bundle
import android.view.View
import com.gailo22.mysuperapp.views.TextRow
import com.gailo22.mysuperapp.views.dividerRow
import com.gailo22.mysuperapp.views.loadingRow
import com.gailo22.mysuperapp.views.screen.ContainerFragment
import com.gailo22.mysuperapp.views.screen.simpleController
import com.gailo22.mysuperapp.views.spaceRow
import com.gailo22.mysuperapp.views.textRow

class GoalFragment : ContainerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun controller() = simpleController {
        spaceRow {
            id("top_spacing")
            size(2)
        }

        textRow {
            id("trending_header")
            body(R.string.trending)
            style(TextRow.TextStyle.BODY)
            paddingStart(8)
        }

        dividerRow {
            id("trending_divider")
            paddingStart(8)
        }

        loadingRow {
            id("trending_loading")
            size(100)
        }

        textRow {
            id("today_header")
            body(R.string.today)
            style(TextRow.TextStyle.BODY)
            paddingStart(8)
        }

        // display cardview with circular progressbar of completed tasks (and text of remaining ones)
        // display list of completed tasks scheduled for that day (some won't have any time others will)
        // goals can be skipped

        dividerRow {
            id("today_divider")
            paddingStart(8)
        }

        loadingRow {
            id("today_loading")
            size(100)
        }

        textRow {
            id("unscheduled_header")
            body(R.string.unscheduled)
            style(TextRow.TextStyle.BODY)
            paddingStart(8)
        }

        dividerRow {
            id("unscheduled_divider")
            paddingStart(8)
        }

        loadingRow {
            id("unscheduled_loading")
            size(100)
        }
    }
}
