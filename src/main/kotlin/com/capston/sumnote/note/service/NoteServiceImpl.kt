package com.capston.sumnote.member.service

import com.capston.sumnote.domain.Member
import com.capston.sumnote.domain.Note
import com.capston.sumnote.domain.NotePage
import com.capston.sumnote.member.repository.MemberRepository
import com.capston.sumnote.note.dto.CreateNoteDto
import com.capston.sumnote.note.repository.NoteRepository
import com.capston.sumnote.note.repository.NotePageRepository
import com.capston.sumnote.util.response.CustomApiResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NoteServiceImpl(
    private val memberRepository: MemberRepository,
    private val noteRepository: NoteRepository,
    private val notePageRepository: NotePageRepository
) : NoteService {

    override fun createNote(dto: CreateNoteDto, email: String): CustomApiResponse<*> {

        // 이메일로 멤버 찾기
        val member = memberRepository.findByEmail(email).orElse(null)
            ?: return CustomApiResponse.createFailWithoutData(404, "Member not found")

        // 노트 생성과 저장
        val note = Note(title = dto.note.title, member = member)
        val savedNote = noteRepository.save(note)

        // 노트 페이지 생성과 저장
        dto.notePages.forEach {
            val notePage = NotePage(
                title = it.title,
                content = it.content,
                note = savedNote
            )
            notePageRepository.save(notePage)
        }

        // 응답
        return CustomApiResponse.createSuccessWithoutData<Unit>(201, "노트가 정상적으로 생성되었습니다.")
    }
}