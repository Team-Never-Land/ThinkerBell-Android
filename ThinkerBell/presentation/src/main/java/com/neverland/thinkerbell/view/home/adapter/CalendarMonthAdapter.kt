package com.neverland.thinkerbell.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.thinkerbell.R
import java.util.Calendar
import java.util.Date

class CalendarMonthAdapter(
    private var scheduleList: List<AcademicSchedule>,
    private val onMonthChange: (Int) -> Unit
) : RecyclerView.Adapter<CalendarMonthAdapter.Month>() {

    val baseCalendar: Calendar = Calendar.getInstance()
    private var currentYear = baseCalendar.get(Calendar.YEAR) // 현재 연도를 저장
    private var currentMonth = baseCalendar.get(Calendar.MONTH) // 현재 월을 저장

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Month {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_month, parent, false)
        return Month(view)
    }

    override fun onBindViewHolder(holder: Month, position: Int) {
        // 리사이클러뷰 초기화
        val listLayout: RecyclerView = holder.view.findViewById(R.id.rv_month)

        // 달 구하기
        val calendar = baseCalendar.clone() as Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - Int.MAX_VALUE / 2) // 정확한 월 계산

        // 연도와 월 설정
        val month = calendar.get(Calendar.MONTH) // 월 (0부터 시작)
        val year = calendar.get(Calendar.YEAR)   // 연도

        // title 텍스트 초기화 (연도 + 월 표시)
        val titleText: TextView = holder.view.findViewById(R.id.tv_month)
        titleText.text = "${year}년 ${month + 1}월"

        // 일 구하기
        val dayList: MutableList<Date> = MutableList(6 * 7) { Date() }
        for (i in 0..5) {
            for (k in 0..6) {
                calendar.set(Calendar.DAY_OF_WEEK, k + 1)
                dayList[i * 7 + k] = calendar.time
            }
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }

        listLayout.layoutManager = GridLayoutManager(holder.view.context, 7)
        listLayout.adapter = CalendarDayAdapter(month, dayList, scheduleList)

        // 화살표 버튼 클릭 이벤트 처리
        val btnPrevMonth: ImageButton = holder.view.findViewById(R.id.btn_prev_month)
        val btnNextMonth: ImageButton = holder.view.findViewById(R.id.btn_next_month)

        btnPrevMonth.setOnClickListener {
            if (currentMonth == 0) {
                currentMonth = 11 // 1월에서 이전으로 가면 12월로 이동
                currentYear-- // 연도를 이전 연도로 변경
            } else {
                currentMonth--
            }
            onMonthChange(position - 1)
        }

        btnNextMonth.setOnClickListener {
            if (currentMonth == 11) {
                currentMonth = 0 // 12월에서 다음으로 가면 1월로 이동
                currentYear++ // 연도를 다음 연도로 변경
            } else {
                currentMonth++
            }
            onMonthChange(position + 1)
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun setData(newList: List<AcademicSchedule>) {
        scheduleList = newList
        notifyDataSetChanged()
    }

    class Month(val view: View) : RecyclerView.ViewHolder(view)
}
