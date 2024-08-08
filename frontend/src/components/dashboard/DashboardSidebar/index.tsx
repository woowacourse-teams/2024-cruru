import { Link } from 'react-router-dom';
import Accordion from '@components/common/Accordion';
import S from './style';

interface Option {
  text: string;
  isSelected: boolean;
  postId: number;
}

interface DashboardSidebarProps {
  options: Option[];
}

export default function DashboardSidebar({ options }: DashboardSidebarProps) {
  return (
    <S.Container>
      <S.Logo>ㅋㄹㄹ</S.Logo>

      <S.Contents>
        <Accordion title={<Link to="/dashboard/1/posts">공고</Link>}>
          {options.map(({ text, isSelected, postId }, index) => (
            // eslint-disable-next-line react/no-array-index-key
            <Accordion.ListItem key={index}>
              <S.LinkContainer isSelected={isSelected}>
                <Link to={`/dashboard/1/${postId}`}>{text}</Link>
              </S.LinkContainer>
            </Accordion.ListItem>
          ))}
        </Accordion>
      </S.Contents>
    </S.Container>
  );
}
