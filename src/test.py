import sys
import os
from subprocess import call

def main(argv):
    port = argv[0]
    query_path = "../data/queries.tsv"
    rev_path = "../data/qrels.tsv"
    result_path = "../results/"

    qf = open(query_path)

    line = qf.readline()
    query_list = []
    while(line):
        query_list.append(line.replace("\n", ""))
        line = qf.readline()

    ranker_list = ["cosine", "QL", "phrase", "numviews", "linear"]
    ranker_files = ['hw1.1-vsm.tsv', 'hw1.1-ql.tsv', 'hw1.1-phrase.tsv', 'hw1.1-numviews.tsv', 'hw1.2-linear.tsv']

    eval_files = ['hw1.3-vsm.tsv', 'hw1.3-ql.tsv', 'hw1.3-phrase.tsv', 'hw1.3-numviews.tsv', 'hw1.3-linear.tsv']

    for ri, r in enumerate(ranker_list):
        rf = ranker_files[ri];
        ef = eval_files[ri];

        rfile_path = os.path.join(result_path, rf)
        efile_path = os.path.join(result_path, ef)
        for qi, q in enumerate(query_list):
            cmd = []
            cmd.append('curl "http://localhost:258')
            cmd.append(port)

            cmd.append('/search?')

            cmd.append('query=')
            cmd.append(q.replace(" ", "%20"))

            cmd.append('&ranker=')
            cmd.append(r)

            cmd.append('&format=text"')

            if qi == 0:
                cmd.append('>')
            else:
                cmd.append('>>')
            cmd.append(rfile_path)

            rcmd = "".join(cmd) # generate cmd for ranker result

            cmd.pop()
            cmd.pop() # remove ">" and "rfile_path"

            cmd.append(" | java edu.nyu.cs.cs2580.Evaluator ")
            cmd.append(rev_path)
            if qi == 0:
                cmd.append('>')
            else:
                cmd.append('>>')
            cmd.append(efile_path)
            ecmd = "".join(cmd) # generate cmd for evaluator result

            print rcmd
            print ecmd
            os.system(rcmd)
            os.system(ecmd)


if __name__ == "__main__":
    if(len(sys.argv) < 2):
        #print 'Please do "python test.py [PORT] [QUERY FILE] [RELEVANCE FILE] [RESULT PATH]"'
        print 'Please do "python test.py [PORT]"'
    else:
        main(sys.argv[1:])
